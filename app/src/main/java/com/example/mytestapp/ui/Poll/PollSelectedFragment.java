package com.example.mytestapp.ui.Poll;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mytestapp.R;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PollSelectedFragment extends Fragment {

    private PollSelectedViewModel mViewModel;

    private int id;
    private PollRepository pollRep;
    private UserRepository userRep;
    public PollSelectedFragment(int id){
        this.id = id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_selected_poll, container, false);

        pollRep = getPollRepository();
        userRep = getUserRepository();


        /*pollRep.getPoll(id,getActivity().getApplication()).observe(getActivity(), poll ->{

            TextView title = root.findViewById(R.id.meetingSelectedTitle);
            title.setText(poll.getTitlePoll());



            userRep.getUserById(poll.getUser_id(),getActivity().getApplication()).observe(getActivity(),user -> {

                TextView name = root.findViewById(R.id.nameOfCreator);
                name.setText(user.getLastName() + " " + user.getFirstName());

            });

            TextView day = root.findViewById(R.id.dayOfMeetingSelected);
            Date date = meeting.getDayMeeting();
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            day.setText(df.format(date));

            TextView place = root.findViewById(R.id.placeMeetingSelected);
            place.setText(meeting.getPlaceMeeting());

            TextView description = root.findViewById(R.id.descriptionMeetingSelected);
            description.setText(meeting.getDescMeeting());


        });*/



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PollSelectedViewModel.class);
        // TODO: Use the ViewModel
    }

    public PollRepository getPollRepository() { return PollRepository.getInstance() ; }
    public UserRepository getUserRepository() { return UserRepository.getInstance() ; }

}
