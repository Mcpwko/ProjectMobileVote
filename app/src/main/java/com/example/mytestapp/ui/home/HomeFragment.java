package com.example.mytestapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mytestapp.MainActivity;
import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.ui.Meeting.MeetingSelectedFragment;
import com.example.mytestapp.ui.Poll.PollSelectedFragment;
import com.example.mytestapp.ui.addVote.ChooseVoteFragment;
import com.example.mytestapp.viewmodel.HomeListViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeListViewModel homeViewModel;
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

        HomeListViewModel.Factory factory = new HomeListViewModel.Factory(
                getActivity().getApplication());

        homeViewModel = ViewModelProviders.of(this,factory).get(HomeListViewModel.class);


        homeViewModel.getMeetings().observe(getActivity(), list-> {


                        for(int i =0 ; i<list.size();i++) {
                            Button button = new Button(getActivity());
                            button.setText(list.get(i).getTitleMeeting());
                            button.setTextColor(getResources().getColor(R.color.TopicsHome));
                            int x = i;
                            button.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    FragmentTransaction transaction;
                                    transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.home, new MeetingSelectedFragment(list.get(x).getMid())).commit();
                                }
                            });
                            linearLayout.addView(button);
                        }
                });

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
                            transaction.replace(R.id.home, new PollSelectedFragment(list.get(x).getPid())).commit();
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
