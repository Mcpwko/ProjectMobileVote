package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Meeting2;
import com.example.mytestapp.db.firebase.MeetingLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
//The class is used to transfer data from DAO to ViewModel

public class MeetingRepository {

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

   /* public LiveData<List<Meeting>> getActiveMeeting(Context context) {
        return AppDatabase.getInstance(context).meetingDao().getActiveMeetings();
    }

    //A voir comment faire
    public LiveData<List<Meeting>> getMyMeetings (int id, Context context) {
        return AppDatabase.getInstance(context).meetingDao().getMyMeetings(id);
    }*/


    public LiveData<Meeting2> getMeeting(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id);
        return new MeetingLiveData(reference);
    }




    public void insertMeeting(final Meeting2 meeting, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("votes").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id)
                .setValue(meeting, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void updateMeeting(final Meeting2 meeting, final OnAsyncEventListener callback) {
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

    public void deleteMeeting(final Meeting2 meeting, final OnAsyncEventListener callback) {
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