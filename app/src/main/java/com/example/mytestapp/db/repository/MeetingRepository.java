package com.example.mytestapp.db.repository;

//The repository class is called by the viewModel. It creates the connection with the database.

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.firebase.MeetingListLiveData;
import com.example.mytestapp.db.firebase.MeetingLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
//The class is used to transfer data from DAO to ViewModel

public class MeetingRepository {

    private static final String TAG = "MeetingRepository";

    private static MeetingRepository instance;

    private MeetingRepository() {}
    //The getInstance method checks to see if an object of that class already exists in the program.
    // It returns null if an object exists.
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


    //This method is used to get all the active meetings from the database

    public LiveData<List<Meeting>> getActiveMeetings() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes");
        return new MeetingListLiveData(reference);
    }


    //This method is used to get a meeting from the database

    public LiveData<Meeting> getMeeting(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id);
        return new MeetingLiveData(reference);
    }

    //This method is used to insert a meeting into the database

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
    //This method is used to update a meeting in the database

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

    //This method is used to delete a meeting in the database

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