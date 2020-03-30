package com.example.mytestapp.ui.Poll;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mytestapp.MainActivity;
import com.example.mytestapp.R;
import com.example.mytestapp.db.async.CreateAttendance;
import com.example.mytestapp.db.async.CreateVote;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.entities.Vote;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.PossibleAnswersRepository;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.db.repository.VoteRepository;
import com.example.mytestapp.ui.home.HomeFragment;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PollSelectedFragment extends Fragment {

    private PollSelectedViewModel mViewModel;

    private int id;
    private PollRepository pollRep;
    private UserRepository userRep;
    private VoteRepository voteRep;
    private PossibleAnswersRepository possibleAnsRep;
    private static final String TAG = "PollSelectedFragment";
    public PollSelectedFragment(int id){
        this.id = id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_selected_poll, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PollSelectedViewModel.class);









    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        pollRep = getPollRepository();
        userRep = getUserRepository();
        possibleAnsRep = getPossibleAnswersRepository();
        voteRep = getVoteRepository();

        if(this.isVisible()){
        pollRep.getPoll(id,getActivity().getApplication()).observe(getActivity(), poll -> {

            TextView title = view.findViewById(R.id.titlePollSelected);
            title.setText(poll.getTitlePoll());

            TextView day = view.findViewById(R.id.deadlinePollSelected);
            Date deadlinePoll = poll.getDeadlinePoll();
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            day.setText(df.format(deadlinePoll));

            TextView category = view.findViewById(R.id.categoryPollSelected);
            category.setText(poll.getCategoryPoll());

            TextView description = view.findViewById(R.id.descriptionPollSelected);
            description.setText(poll.getDescPoll());


            /*userRep.getUserById(poll.getUser_id(),getActivity().getApplication()).observe(getActivity(),user -> {

                TextView name = view.findViewById(R.id.userCreatorPoll);
                name.setText(user.getLastName() + " " + user.getFirstName());

            });*/


            SharedPreferences preferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = preferences.getString("User", "");
            User usertest = gson.fromJson(json, User.class);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayoutAnswersPollSelected);

            voteRep.getVote(usertest.getUid(),poll.getPid(),getActivity().getApplication()).observe(getActivity(), vote ->{
                   linearLayout.removeAllViews();

                   possibleAnsRep.getPossibleAnswersByPoll(poll.getPid(),getActivity().getApplication()).observe(getActivity(), possibleAnswers -> {
                       if(vote.size()>=1){
                           List<TextView> list = new ArrayList<TextView>();
                           for(int i =0 ; i<possibleAnswers.size();i++) {
                               boolean exist =false;
                               for (Vote customer : vote) {
                                   if (customer.getPossaid()==possibleAnswers.get(i).getPaid()) {
                                       exist = true;
                                   }
                               }


                               if(exist){
                                   TextView button = new TextView(getActivity());
                                   button.setText(possibleAnswers.get(i).getAnswer());
                                   button.setId(possibleAnswers.get(i).getPaid());
                                   button.setTextColor(getResources().getColor(R.color.TopicsHome));
                                   list.add(button);

                                   linearLayout.addView(button);
                               }else{

                                   TextView button = new TextView(getActivity());
                                   button.setText(possibleAnswers.get(i).getAnswer());
                                   button.setId(possibleAnswers.get(i).getPaid());
                                   list.add(button);

                                   linearLayout.addView(button);
                               }

                           }

                       }else{

                           List<CheckBox> list = new ArrayList<CheckBox>();

                           for(int i =0 ; i<possibleAnswers.size();i++) {

                               CheckBox button = new CheckBox(getActivity());
                               button.setText(possibleAnswers.get(i).getAnswer());
                               button.setId(possibleAnswers.get(i).getPaid());
                               list.add(button);
                               linearLayout.addView(button);
                           }

                           Button validate = view.findViewById(R.id.validateAnswers);

                           validate.setOnClickListener(new View.OnClickListener() {
                               public void onClick(View v) {

                                   List<Integer> listChoosenAnswers = new ArrayList<Integer>();

                                   for(int i=0;i<list.size();i++){
                                       if(list.get(i).isChecked()==true){
                                           listChoosenAnswers.add(list.get(i).getId());

                                           Vote vote = new Vote();

                                           SharedPreferences preferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                                           Gson gson = new Gson();
                                           String json = preferences.getString("User", "");
                                           User user = gson.fromJson(json, User.class);


                                           vote.setUser_id(user.getUid());
                                           vote.setPossaid(list.get(i).getId());
                                           vote.setPoll_id(poll.getPid());


                                           new CreateVote(getActivity().getApplication(), new OnAsyncEventListener() {
                                               @Override
                                               public void onSuccess() {
                                                   Log.d(TAG, "createUserWithEmail: success");
                                               }

                                               @Override
                                               public void onFailure(Exception e) {
                                                   Log.d(TAG, "createUserWithEmail: failure", e);
                                               }
                                           }).execute(vote);



                                       }
                                   }
                                    Activity a = getActivity();
                                   Intent intent = new Intent(a, MainActivity.class);
                                   startActivity(intent);
                                   a.finish();


                                   //FragmentTransaction transaction;
                                   //transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                   //transaction.replace(R.id.home, new HomeFragment()).commit();
                               }

                           });
                       }




                   });
               });
            });}

    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.sort);
        item.setVisible(false);
    }

    public PollRepository getPollRepository() { return PollRepository.getInstance() ; }
    public UserRepository getUserRepository() { return UserRepository.getInstance() ; }
    public PossibleAnswersRepository getPossibleAnswersRepository() { return PossibleAnswersRepository.getInstance() ; }
    public VoteRepository getVoteRepository() {return VoteRepository.getInstance() ;}
}
