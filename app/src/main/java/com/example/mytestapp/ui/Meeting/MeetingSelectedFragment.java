package com.example.mytestapp.ui.Meeting;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.example.mytestapp.MainActivity;
import com.example.mytestapp.R;
import com.example.mytestapp.db.async.DeleteAttendance;
import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.AttendanceRepository;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Handler;

import static android.content.Context.MODE_PRIVATE;

public class MeetingSelectedFragment extends Fragment {
    private static final String TAG = "MeetingSelectedFragment";
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

        boolean visible = this.isVisible();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

            }
        }, 1000);

        boolean visible2 = this.isVisible();

        meetingRep = getMeetingRepository();
        userRepository = getUserRepository();
        attendanceRepository = getAttendanceRepository();


        meetingRep.getMeeting(id,getActivity().getApplication()).observe(getActivity(), meeting ->{

            TextView title = root.findViewById(R.id.meetingSelectedTitle);
            title.setText(meeting.getTitleMeeting());



            SharedPreferences preferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = preferences.getString("User", "");
            User actualUser = gson.fromJson(json, User.class);

            attendanceRepository.getAttendance(actualUser.getUid(),meeting.getMid(),getActivity().getApplication()).observe(getActivity(), attendance ->{
                int idtest = meeting.getUser_id();
                System.out.println("salut");

                /*SharedPreferences preferencescreator = getActivity().getSharedPreferences("UserCreator", MODE_PRIVATE);
                Gson gson2 = new Gson();
                String json2 = preferencescreator.getString("UserCreator", "");
                User userCreator = gson2.fromJson(json2, User.class);

                if(userCreator==null || meeting.getUser_id()!=userCreator.getUid()) {*/
                    userRepository.getUserById(meeting.getUser_id(), getActivity().getApplication()).observe(getActivity(), user -> {

                        SharedPreferences creator = getActivity().getApplicationContext().getSharedPreferences("UserCreator", MODE_PRIVATE);
                        SharedPreferences.Editor edt = creator.edit();

                        Gson gson3 = new Gson();
                        String json3 = gson3.toJson(user);
                        edt.putString("UserCreator", json3);
                        edt.apply();

                        TextView name = root.findViewById(R.id.nameOfCreator);
                        name.setText(user.getLastName() + " " + user.getFirstName());
                    });
                /*}else {

                    TextView name = root.findViewById(R.id.nameOfCreator);
                    name.setText(userCreator.getLastName() + " " + userCreator.getFirstName());
                }*/

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


                Button btnEdit = root.findViewById(R.id.editmeeting);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle(getString(R.string.delete));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getString(R.string.deleteAttedance));
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yesmeeting), (dialog, which) -> {
                            btnyes.setVisibility(View.VISIBLE);
                            btnNo.setVisibility(View.VISIBLE);
                            btnEdit.setVisibility(View.GONE);

                            attendanceRepository.getAttendanceById(id, getActivity().getApplication()).observe(getActivity(), attendance -> {

                                new DeleteAttendance(getActivity().getApplication(), new OnAsyncEventListener() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d(TAG, "createUserWithEmail: success");
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        Log.d(TAG, "createUserWithEmail: failure", e);
                                    }
                                }).execute(attendance);

                            });



                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.Nobtn), (dialog, which) -> alertDialog.dismiss());
                        alertDialog.show();


                    }
                });


                if(attendance==null){
                    btnyes.setVisibility(View.VISIBLE);
                    btnNo.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.GONE);
                    btnyes.setId(meeting.getMid());
                    btnNo.setId(meeting.getMid());


                }else{
                    btnyes.setVisibility(View.GONE);
                    btnNo.setVisibility(View.GONE);
                    btnEdit.setVisibility(View.VISIBLE);
                    btnEdit.setId(attendance.getAid());
                }
            });


        });




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
