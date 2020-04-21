package com.example.mytestapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.ui.Meeting.MeetingSelectedFragment;
import com.example.mytestapp.ui.Poll.PollSelectedFragment;
import com.example.mytestapp.viewmodel.HomeListViewModel;

import java.util.List;

//We called this class "Home" because it's the main fragment of our App, you will find here
//the declaration of the list of Polls and Meetings
public class HomeFragment extends Fragment {

    private HomeListViewModel homeViewModel;
    //Here we are going to use the repositories created to get the data from the Database
    private PollRepository pollRep;
    private MeetingRepository meetingRep;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();

        }


        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.LinearLayoutHome);

        //Create an instance of the ViewModel
        //It's important to notice that we can't use "this" for the context, because we are in a
        //fragment. We use then getActivity().getApplication()
        HomeListViewModel.Factory factory = new HomeListViewModel.Factory(
                getActivity().getApplication());


        homeViewModel = ViewModelProviders.of(this,factory).get(HomeListViewModel.class);

        //We are creating all buttons for the meetings from the Database
        homeViewModel.getMeetings().observe(getActivity(), list-> {



                        //The IF is used to avoid the NullPointerException which returns true if the
                        //fragment has been explicitly detached from the UI
                        if(isAdded())
                            //We create as many buttons as we need for meetings
                        for(int i =0 ; i<list.size();i++) {

                            if(list.get(i)!=null) {
                                Button button = new Button(getActivity());
                                button.setText(list.get(i).getTitleMeeting());
                                button.setTextColor(getResources().getColor(R.color.TopicsHome));
                                int x = i;
                                button.setOnClickListener(new View.OnClickListener() {
                                    //We give then the action to change the fragment when the user of
                                    //the app click on a button of the list of Meetings
                                    public void onClick(View v) {
                                        FragmentTransaction transaction;
                                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("SelectedItem", list.get(x).getMid());
                                        editor.apply();
                                        transaction.replace(R.id.home, new MeetingSelectedFragment()).commit();
                                    }
                                });
                                //We create a linear Layout with a scroll view to allow the user to
                                //create as many buttons he wants
                                linearLayout.addView(button);
                            }
                        }
                });

        //we do exactly the same thing with Polls
        homeViewModel.getPolls().observe(getViewLifecycleOwner(), new Observer<List<Poll>>() {
            @Override
            public void onChanged(List<Poll> list) {

                for(int i =0 ; i<list.size();i++) {
                    Button button = new Button(getContext());
                    button.setText(list.get(i).getTitlePoll());
                    int x = i;
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            FragmentTransaction transaction;
                            transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("SelectedItem", list.get(x).getPid());
                            editor.apply();
                            transaction.replace(R.id.home, new PollSelectedFragment()).commit();
                        }
                    });
                    linearLayout.addView(button);
                }

            }
        });


        return root;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.sort);
        item.setVisible(true);
    }

}
