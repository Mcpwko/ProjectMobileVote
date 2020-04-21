package com.example.mytestapp.ui.mytopics;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;

import com.example.mytestapp.ui.mytopics.Meeting.MyTopicMeetingFragment;
import com.example.mytestapp.ui.mytopics.Poll.MyTopicPollFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

//MyTopics contains all the Polls and Meetings that we created
public class MyTopicsFragment extends Fragment {

    private MyTopicsViewModel mViewModel;
    private PollRepository pollRep;
    private MeetingRepository meetingRep;
    public static MyTopicsFragment newInstance() {
        return new MyTopicsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_mytopics, container, false);



        LinearLayout linearLayout = root.findViewById(R.id.linearLayoutMyTopics);


        pollRep = getPollRepository();
        meetingRep = getMeetingRepository();


        FirebaseUser actual = FirebaseAuth.getInstance().getCurrentUser();
        //This part is reserved for creating all the buttons corresponding to a Poll
        pollRep.getActivePolls().observe(getActivity(), list -> {
            List<Poll> myPolls = new ArrayList<>();
            if (list.size() >= 1){
                for (Poll poll : list) {
                    if (poll.getUser_id().equals(actual.getUid())) {
                        myPolls.add(poll);
                    }
                }

            for (int i = 0; i < myPolls.size(); i++) {
                if(isAdded()){
                Button button = new Button(getActivity());
                button.setText(myPolls.get(i).getTitlePoll());
                int x = i;
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        FragmentTransaction transaction;
                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("SelectedMyItem", myPolls.get(x).getPid());
                        editor.apply();
                        transaction.replace(R.id.my_topics, new MyTopicPollFragment()).commit();
                    }
                });
                linearLayout.addView(button);
                }
            }
        }
        });

        //This part is reserved for creating all the buttons corresponding to a Meeting

        meetingRep.getActiveMeetings().observe(getActivity(), list ->{
            List<Meeting> myMeetings = new ArrayList<>();
            if (list.size() >= 1) {
                for (Meeting meeting : list) {
                    if (meeting.getUser_id().equals(actual.getUid())) {
                        myMeetings.add(meeting);
                    }
                }

                for (int i = 0; i < myMeetings.size(); i++) {
                    if (isAdded()){
                        Button button = new Button(getActivity());
                    button.setText(myMeetings.get(i).getTitleMeeting());
                    button.setTextColor(getResources().getColor(R.color.TopicsHome));
                    int x = i;
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            FragmentTransaction transaction;
                            transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("SelectedMyItem", myMeetings.get(x).getMid());
                            editor.apply();
                            transaction.replace(R.id.my_topics, new MyTopicMeetingFragment()).commit();
                        }
                    });
                    linearLayout.addView(button);
                }
                }
            }

        });




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyTopicsViewModel.class);
        // TODO: Use the ViewModel
    }

    //Those methods are used to get PollRepository and MeetingRepository
    public PollRepository getPollRepository() {
        return PollRepository.getInstance();
    }
    public MeetingRepository getMeetingRepository() { return MeetingRepository.getInstance() ; }

}
