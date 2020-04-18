package com.example.mytestapp.ui.Meeting;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.User;

import com.example.mytestapp.util.OnAsyncEventListener;

import com.example.mytestapp.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



import static android.content.Context.MODE_PRIVATE;
//This class is launched when we select a fragment on the list of the topics
public class MeetingSelectedFragment extends Fragment {
    private static final String TAG = "MeetingSelectedFragment";
    private MeetingSelectedViewModel mViewModel;
    private int idMeeting;
    private UserViewModel userViewModel;


    public MeetingSelectedFragment(int idMeeting){
        this.idMeeting = idMeeting;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_meeting_selected, container, false);
        setHasOptionsMenu(true);


        //We create a sharedPreference to get the name of the User
        SharedPreferences preferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User actualUser = gson.fromJson(json, User.class);


        /*MeetingSelectedViewModel.Factory factory = new MeetingSelectedViewModel.Factory(
                getActivity().getApplication(),idMeeting,actualUser.getUid());

        mViewModel = ViewModelProviders.of(this,factory).get(MeetingSelectedViewModel.class);


        //To set the text the EditTexts created in the layout, we need to get the data in the
        //in the database, that's why we use a userViewModel which search for the Meetings associated
        //to a meeting
        /*mViewModel.getMeeting().observe(getActivity(), meeting-> {
            if(this.isVisible()) {
                UserViewModel.Factory factoryUser = new UserViewModel.Factory(getActivity().getApplication(), meeting.getUser_id());
                userViewModel = ViewModelProviders.of(this, factoryUser).get(UserViewModel.class);
            }

                    TextView title = root.findViewById(R.id.titleMeetingSelected);
                    title.setText(meeting.getTitleMeeting());

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

                    userViewModel.getUser().observe(getActivity(), user -> {
                        TextView name = root.findViewById(R.id.nameOfCreatorMeeting);
                        name.setText(user.getLastName() + " " + user.getFirstName());
                    });

                    Button btnyes = root.findViewById(R.id.yesBtnMeetingSelected);
                    Button btnNo = root.findViewById(R.id.noBtnMeetingSelected);
                    TextView response = root.findViewById(R.id.responsePresence);

                    Button btnEdit = root.findViewById(R.id.editmeeting);

                    mViewModel.getAttendance().observe(getActivity(), attendance -> {



                        //We are creating here an edit btn to make changed possible for the user
                        btnEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //The AlertDialog asks the user if he is sure to change his answer
                                //to the meeting
                                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle(getString(R.string.delete));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.deleteAttedance));
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yesmeeting), (dialog, which) -> {
                                    //We make some buttons visible or not visible when we click on the
                                    //Edit button
                                    btnyes.setVisibility(View.VISIBLE);
                                    btnNo.setVisibility(View.VISIBLE);
                                    btnEdit.setVisibility(View.GONE);
                                    response.setVisibility(View.GONE);


                                    //We then proceed with the deletion of the Attendance
                                    /*new DeleteAttendance(getActivity().getApplication(), new OnAsyncEventListener() {
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
                                /*alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.Nobtn), (dialog, which) -> alertDialog.dismiss());
                                alertDialog.show();*/
/*

                            }
                        });

                        //if the attendance in the database is null we set the visibility of some
                        //buttons. It corresponds to the normal situation when whe don't click
                        //on the edit button

                        if (attendance == null) {
                            btnyes.setVisibility(View.VISIBLE);
                            btnNo.setVisibility(View.VISIBLE);
                            btnEdit.setVisibility(View.GONE);
                            response.setVisibility(View.GONE);

                            btnyes.setId(meeting.getMid());
                            btnNo.setId(meeting.getMid());


                        } else {

                            //If the attendance is not null we can edit the Meeting saying yes or No
                            String reponse;
                            if(attendance.isAnswerAttendance()) {
                                reponse = "Yes";
                            }else {
                                reponse = "No";
                            }
                            //We keep the response on the display
                            response.setText(reponse);

                            btnyes.setVisibility(View.GONE);
                            btnNo.setVisibility(View.GONE);
                            btnEdit.setVisibility(View.VISIBLE);
                            response.setVisibility(View.VISIBLE);
                            btnEdit.setId(attendance.getAid());
                        }

                    });
        });*/




        return root;
    }


    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.sort);
        item.setVisible(false);
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MeetingSelectedViewModel.class);
        // TODO: Use the ViewModel
    }

}
