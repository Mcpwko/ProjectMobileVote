package com.example.mytestapp.ui.mytopics;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;
import com.google.gson.Gson;

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


        SharedPreferences preferences = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User user = gson.fromJson(json, User.class);




        pollRep.getMyPolls(user.getUid(),getActivity().getApplication()).observe(getActivity(), list ->{

            for(int i =0 ; i<list.size();i++) {
                Button button = new Button(getActivity());
                button.setText(list.get(i).getTitlePoll());
                linearLayout.addView(button);
            }
        });

        meetingRep.getMyMeetings(user.getUid(),getActivity().getApplication()).observe(getActivity(), list ->{

            for(int i =0 ; i<list.size();i++) {
                Button button = new Button(getActivity());
                button.setText(list.get(i).getTitleMeeting());
                button.setTextColor(getResources().getColor(R.color.TopicsHome));
                linearLayout.addView(button);
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


    public PollRepository getPollRepository() {
        return PollRepository.getInstance();
    }
    public MeetingRepository getMeetingRepository() { return MeetingRepository.getInstance() ; }

}
