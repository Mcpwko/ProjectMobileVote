package com.example.mytestapp.ui.mytopics.Meeting;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.Attendance;

import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.ui.Meeting.MeetingSelectedViewModel;
import com.example.mytestapp.ui.mytopics.MyTopicsFragment;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.example.mytestapp.viewmodel.AttendanceViewModel;
import com.google.gson.Gson;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

//This class is used when we click on one of a Meeting we created
public class MyTopicMeetingFragment extends Fragment {

    private MeetingSelectedViewModel mViewModel;
    private AttendanceViewModel attendanceViewModel;
    private int idMeeting;
    private static final String TAG = "MyTopicMeetingFragment";
    public MyTopicMeetingFragment(int idMeeting){
        this.idMeeting = idMeeting;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragmentmy_topic_meeting, container, false);

        //The sharedPreferences is always used to keep the information of the User (especially his name)
        SharedPreferences preferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User actualUser = gson.fromJson(json, User.class);

        AttendanceViewModel.Factory factoryAttendance = new AttendanceViewModel.Factory(idMeeting, getActivity().getApplication());
        attendanceViewModel = ViewModelProviders.of(this,factoryAttendance).get(AttendanceViewModel.class);

        /*MeetingSelectedViewModel.Factory factory = new MeetingSelectedViewModel.Factory(
                getActivity().getApplication(),idMeeting,actualUser.getUid());

        mViewModel = ViewModelProviders.of(this,factory).get(MeetingSelectedViewModel.class);

        //With this viewModel we can use all the methods we want from the table Meeting
        mViewModel.getMeeting().observe(getActivity(), meeting-> {
            if(isAdded()){

                //We set the text of the editTexts with the data we have in the Database

            EditText title = root.findViewById(R.id.titleMeetingMyTopic);
            title.setText(meeting.getTitleMeeting());

            DatePicker day = root.findViewById(R.id.dayMeetingMyTopic);
            Date date = meeting.getDayMeeting();
            day.init(date.getYear(),date.getMonth(),date.getDay(),null);
            day.setMinDate(meeting.getDayMeeting().getTime());
            day.setEnabled(false);

            TimePicker hour = root.findViewById(R.id.hourMeetingMyTopic);
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm");
                try {
                    Date time = dateFormat2.parse(meeting.getTimeMeeting());
                    hour.setHour(time.getHours());
                    hour.setMinute((time.getMinutes()));
                    hour.setEnabled(false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            EditText place = root.findViewById(R.id.placeMeetingMyTopic);
            place.setText(meeting.getPlaceMeeting());

            EditText description = root.findViewById(R.id.descMeetingMyTopic);
            description.setText(meeting.getDescMeeting());


            //We do the same with the viewModel for attendance
            attendanceViewModel.getAttendances().observe(getActivity(), attendances -> {

                TextView yes = root.findViewById(R.id.cptYesMeeting);
                TextView no = root.findViewById(R.id.cptNoMeeting);
                //We count the number of yes and no
                int cptyes = 0;
                int cptno = 0;

                //Everytime an attendance is "true" we increment yes
                for(Attendance attendance : attendances){
                    if(attendance.isAnswerAttendance()){
                        cptyes++;
                    }else{
                        //Everytime an attendance is "false" we increment yes
                        cptno++;
                    }
                }

                yes.setText(cptyes + " people");
                no.setText(cptno + " people");

            });


            Button delete = root.findViewById(R.id.deleteMeeting);
            Button edit = root.findViewById(R.id.editMeetingMyTopic);
            Button save  = root.findViewById(R.id.saveMeetingMyTopic);


            //We give the possibility to the User to save the data by clicking on the save Button
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    title.setEnabled(false);
                    place.setEnabled(false);
                    description.setEnabled(false);
                    day.setEnabled(false);
                    hour.setEnabled(false);
                    day.setClickable(false);
                    hour.setClickable(false);


                    meeting.setDescMeeting(description.getText().toString());
                    meeting.setPlaceMeeting(place.getText().toString());
                    meeting.setTitleMeeting(title.getText().toString());
                    int a =hour.getCurrentHour();
                    int b = hour.getCurrentMinute();
                    meeting.setTimeMeeting(a +":"+ b);
                    Date date2 = getDateFromDatePicker(day);
                    meeting.setDayMeeting(date2);



                    new UpdateMeeting(getActivity().getApplication(), new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "updateMeeting: success");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "updateMeeting: failure", e);
                        }
                    }).execute(meeting);
                }
            });

            //If we click on the delete button we delete the answers
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DELETE
                    new DeleteMeeting(getActivity().getApplication(), new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "deleteMeeting: success");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "deleteMeeting: failure", e);
                        }
                    }).execute(meeting);

                    //We go back to My topics
                    FragmentTransaction transaction;
                    transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.my_topics, new MyTopicsFragment()).commit();
                }
            });



            //We explain here what button is going to remain or not if we click on the edit button
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title.setEnabled(true);
                    place.setEnabled(true);
                    description.setEnabled(true);
                    day.setEnabled(true);
                    hour.setEnabled(true);
                    edit.setVisibility(View.GONE);
                    save.setVisibility(View.VISIBLE);
                    day.setClickable(true);
                    hour.setClickable(true);
                }
            });

            }


        });*/


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MeetingSelectedViewModel.class);
        // TODO: Use the ViewModel
    }

    //This method is used to get the Date from the datepicker
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

}
