package com.example.mytestapp.db.repository;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.firebase.MeetingListLiveData;
import com.example.mytestapp.db.firebase.MeetingLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
//The class is used to transfer data from DAO to ViewModel

public class MeetingRepository {

    private static final String TAG = "MeetingRepository";

    private static MeetingRepository instance;

    private MeetingRepository() {}

    public static MeetingRepository getInstance() {
        if (instance == null) {
            synchronized (MeetingRepository.class) {
                if (instance == null) {
                    instance = new MeetingRepository();
                }
            }
        }
        return instance;
    }

    //A voir comment faire

    public LiveData<List<Meeting>> getActiveMeetings() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes");
        return new MeetingListLiveData(reference);
    }


    //A voir comment faire
    /*public LiveData<List<Meeting>> getMyMeetings (int id, Context context) {
        return AppDatabase.getInstance(context).meetingDao().getMyMeetings(id);
    }*/


    public LiveData<Meeting> getMeeting(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id);
        return new MeetingLiveData(reference);
    }




    public void insert(final Meeting meeting, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(meeting.getUser_id());
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(key)
                .setValue(meeting, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void updateMeeting(final Meeting meeting, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(meeting.getMid())
                .updateChildren(meeting.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void deleteMeeting(final Meeting meeting, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(meeting.getMid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}