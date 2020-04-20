package com.example.mytestapp.ui.mytopics.Poll;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mytestapp.R;
import com.example.mytestapp.ui.Poll.PollSelectedViewModel;
import com.example.mytestapp.viewmodel.VoteListViewModel;

//This class is used when we click on one of the Poll we created
public class MyTopicPollFragment extends Fragment {

    private PollSelectedViewModel mViewModel;
    private static final String TAG = "MyTopicPollFragment";
    private VoteListViewModel voteListViewModel;
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

        PollSelectedViewModel.Factory factory = new PollSelectedViewModel.Factory(
                getActivity().getApplication(),idPoll);

        mViewModel = ViewModelProviders.of(this,factory).get(PollSelectedViewModel.class);

        /*mViewModel.getPoll().observe(getActivity(),poll -> {

            //If the poll object is not null we can set the text of all our EditText
            if (poll != null){
                TextView title = root.findViewById(R.id.titlePollMyTopic);
            title.setText(poll.getTitlePoll());

            TextView day = root.findViewById(R.id.deadlinePollMyTopic);
            Date deadlinePoll = poll.getDeadlinePoll();
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            day.setText(df.format(deadlinePoll));

            TextView category = root.findViewById(R.id.categoryPollMyTopic);
            category.setText(poll.getCategoryPoll());

            TextView description = root.findViewById(R.id.descPollMyTopic);
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
                        List<TextView> list = new ArrayList<TextView>();
                        for (int i = 0; i < possibleAnswers.size(); i++) {
                            int cpt = 0;
                            for (Vote vote : votes) {
                                if (vote.getPossaid() == possibleAnswers.get(i).getPaid()) {
                                    cpt++;
                                }
                            }

                            if (isAdded()) {

                                TextView textView = new TextView(getActivity());
                                textView.setText(possibleAnswers.get(i).getAnswer() + "    " + cpt + " votes");
                                textView.setId(possibleAnswers.get(i).getPaid());
                                list.add(textView);

                                linearLayout.addView(textView);
                            }


                            //If we click on the delete button we delete Poll and we get back to
                            // the main activity
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //DELETE BUTTON


                                    /*new DeletePoll(getActivity().getApplication(), new OnAsyncEventListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.d(TAG, "createUserWithEmail: success");
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "createUserWithEmail: failure", e);
                                        }
                                    }).execute(poll);


                                    Activity a = getActivity();
                                    Intent intent = new Intent(a, MainActivity.class);
                                    startActivity(intent);
                                    a.finish();


                                }
                            });

                        }
                    });
                }
            });

        }
        });
*/

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PollSelectedViewModel.class);

    }

}
