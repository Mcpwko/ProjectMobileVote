package com.example.mytestapp.ui.Meeting;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.AttendanceRepository;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.UserRepository;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Handler;

public class MeetingSelectedFragment extends Fragment {

    private MeetingSelectedViewModel mViewModel;
    private int id;
    private static final int EDIT_CLIENT = 1;
    private MeetingRepository meetingRep;
    private UserRepository userRepository;
    private AttendanceRepository attendanceRepository;

    public MeetingSelectedFragment(int id){
        this.id = id;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_meeting_selected, container, false);
        setHasOptionsMenu(true);
        final Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms




                meetingRep = getMeetingRepository();
                userRepository = getUserRepository();
                attendanceRepository = getAttendanceRepository();


                meetingRep.getMeeting(id,getActivity().getApplication()).observe(getActivity(), meeting ->{

                    TextView title = root.findViewById(R.id.meetingSelectedTitle);
                    title.setText(meeting.getTitleMeeting());



                    SharedPreferences preferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = preferences.getString("User", "");
                    User actualUser = gson.fromJson(json, User.class);

                    attendanceRepository.getAttendance(actualUser.getUid(),meeting.getMid(),getActivity().getApplication()).observe(getActivity(), attendance ->{
                        userRepository.getUserById(meeting.getUser_id(),getActivity().getApplication()).observe(getActivity(),user -> {
                            TextView name = root.findViewById(R.id.nameOfCreator);
                            name.setText(user.getLastName() + " " + user.getFirstName());
                        });

                        TextView day = root.findViewById(R.id.dayOfMeetingSelected);
                        Date date = meeting.getDayMeeting();
                        DateFormat df = new SimpleDateFormat("dd/MM/yy");
                        day.setText(df.format(date));

                        TextView hour = root.findViewById(R.id.hourMeetingSelected);
                        hour.setText(meeting.getTimeMeeting());

                        TextView place = root.findViewById(R.id.placeMeetingSelected);
                        place.setText(meeting.getPlaceMeeting());

                        TextView description = root.findViewById(R.id.descriptionMeetingSelected);
                        description.setText(meeting.getDescMeeting());

                        Button btnyes = root.findViewById(R.id.yesMeetingbtn);
                        Button btnNo = root.findViewById(R.id.noMeetingbtn);


                        if(attendance==null){
                            btnyes.setId(meeting.getMid());
                            btnNo.setId(meeting.getMid());


                        }else{
                            root.findViewById(R.id.yesMeetingbtn).setVisibility(View.GONE);
                            root.findViewById(R.id.noMeetingbtn).setVisibility(View.GONE);
                            root.findViewById(R.id.editmeeting).setVisibility(View.VISIBLE);
                        }
                    });


                });


            }
        }, 500);




        return root;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.sort);
        item.setVisible(false);
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {

        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MeetingSelectedViewModel.class);
        // TODO: Use the ViewModel
    }

    public AttendanceRepository getAttendanceRepository(){ return AttendanceRepository.getInstance() ;}
    public MeetingRepository getMeetingRepository() { return MeetingRepository.getInstance() ; }
    public UserRepository getUserRepository() { return UserRepository.getInstance() ; }

}
