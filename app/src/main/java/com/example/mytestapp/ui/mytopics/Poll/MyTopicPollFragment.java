package com.example.mytestapp.ui.mytopics.Poll;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mytestapp.MainActivity;
import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.db.entities.Vote;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.PossibleAnswersRepository;
import com.example.mytestapp.db.repository.VoteRepository;
import com.example.mytestapp.ui.Poll.PollSelectedViewModel;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.example.mytestapp.viewmodel.VoteListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mytestapp.MainActivity.getDateFromDatePicker;

//This class is used when we click on one of the Poll we created
public class MyTopicPollFragment extends Fragment {

    private PollSelectedViewModel mViewModel;
    private static final String TAG = "MyTopicPollFragment";
    private VoteListViewModel voteListViewModel;
    private PollRepository pollRepository;
    private VoteRepository voteRepository;
    private PossibleAnswersRepository possibleAnswersRepository;
    public MyTopicPollFragment(){
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragmentmy_topic_poll, container, false);
        String idPoll = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("SelectedMyItem", "NotFound");
        LinearLayout linearLayout = root.findViewById(R.id.linearLayoutPollsMyTopics);

        Button delete = root.findViewById(R.id.deletePoll);
        Button edit = root.findViewById(R.id.editPollMyTopic);
        Button save  = root.findViewById(R.id.savePollMyTopic);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        pollRepository = getPollRepository();
        voteRepository = getVoteRepository();
        possibleAnswersRepository = getPossibleAnswersRepository();

        PollSelectedViewModel.Factory factory = new PollSelectedViewModel.Factory(
                getActivity().getApplication(),idPoll);

        mViewModel = ViewModelProviders.of(this,factory).get(PollSelectedViewModel.class);

        mViewModel.getPoll().observe(getActivity(),poll -> {

            //If the poll object is not null we can set the text of all our EditText
            if (poll != null){
                EditText title = root.findViewById(R.id.titlePollMyTopic);
                title.setText(poll.getTitlePoll());

                DatePicker day = root.findViewById(R.id.deadlinePollMyTopic);
                Date date = poll.getDeadlinePoll();
                day.init(date.getYear(),date.getMonth(),date.getDay(),null);
                day.setMinDate(poll.getDeadlinePoll().getTime());
                day.setEnabled(false);

                Spinner category = root.findViewById(R.id.categoryPollMyTopic);
                String categoryselected = poll.getCategoryPoll();


                category.setAdapter(myAdapter);

                for(int i=0; i<myAdapter.getCount();i++){
                    if(myAdapter.getItem(i).equals(categoryselected)) {
                        category.setSelection(i);
                        category.setEnabled(false);
                    }

                }

                EditText description = root.findViewById(R.id.descPollMyTopic);
                description.setText(poll.getDescPoll());

                if(isAdded())
            mViewModel.getPossibleAnswers().observe(getActivity(), possibleAnswers -> {
                if(isAdded()){
                VoteListViewModel.Factory factoryVote = new VoteListViewModel.Factory(poll.getPid(), getActivity().getApplication());
                voteListViewModel = ViewModelProviders.of(this, factoryVote).get(VoteListViewModel.class);
                if (this.isVisible())
                    voteListViewModel.getVotes().observe(getActivity(), votes -> {

                        //We increment the cpt when possible answers matches with vote. We use that
                        //to know how much people vote for an answer
                        linearLayout.removeAllViews();
                        List<TextView> list = new ArrayList<TextView>();
                        for (int i = 0; i < possibleAnswers.size(); i++) {
                            int cpt = 0;
                            for (Vote vote : votes) {
                                if (vote.getPossaid().equals( possibleAnswers.get(i).getPaid())) {
                                    cpt++;
                                }
                            }

                            if (isAdded()) {

                                TextView textView = new TextView(getActivity());
                                textView.setText(possibleAnswers.get(i).getAnswer() + "    " + cpt + " votes");
                                textView.setHint(possibleAnswers.get(i).getPaid());
                                list.add(textView);

                                linearLayout.addView(textView);
                            }


                            //If we click on the delete button we delete Poll and we get back to
                            // the main activity
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //DELETE POLLL
                                    OnAsyncEventListener callback = new OnAsyncEventListener() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onFailure(Exception e) {

                                        }
                                    };

                                    pollRepository.deletePoll(poll,callback);
                                    for(Vote vote :votes){
                                        voteRepository.deleteVote(vote,callback);
                                    }
                                    for(PossibleAnswers possibleAns : possibleAnswers) {
                                        possibleAnswersRepository.deletePossibleAnswers(possibleAns, callback);
                                    }


                                    Activity a = getActivity();
                                    Intent intent = new Intent(a, MainActivity.class);
                                    startActivity(intent);
                                    a.finish();


                                }
                            });

                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    save.setVisibility(View.GONE);
                                    edit.setVisibility(View.VISIBLE);
                                    title.setEnabled(false);
                                    category.setEnabled(false);
                                    description.setEnabled(false);
                                    day.setEnabled(false);
                                    day.setClickable(false);


                                    poll.setDescPoll(description.getText().toString());
                                    poll.setCategoryPoll(category.getSelectedItem().toString());
                                    poll.setTitlePoll(title.getText().toString());
                                    Date date2 = getDateFromDatePicker(day);
                                    poll.setDeadlinePoll(date2);



                                    //UPDATE INFOS DU MEETING
                                    OnAsyncEventListener callback = new OnAsyncEventListener() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onFailure(Exception e) {

                                        }
                                    };

                                    pollRepository.updatePoll(poll,callback);



                                }
                            });


                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    title.setEnabled(true);
                                    category.setEnabled(true);
                                    description.setEnabled(true);
                                    day.setEnabled(true);
                                    edit.setVisibility(View.GONE);
                                    save.setVisibility(View.VISIBLE);
                                    day.setClickable(true);
                                }
                            });

                        }
                    });
                }
            });

        }
        });


        return root;
    }

    public PollRepository getPollRepository(){return PollRepository.getInstance();}
    public VoteRepository getVoteRepository(){return VoteRepository.getInstance();}
    public PossibleAnswersRepository getPossibleAnswersRepository(){return PossibleAnswersRepository.getInstance();}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PollSelectedViewModel.class);

    }

}
