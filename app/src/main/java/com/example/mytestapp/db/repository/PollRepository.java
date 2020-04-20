package com.example.mytestapp.db.repository;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.firebase.MeetingLiveData;
import com.example.mytestapp.db.firebase.PollListLiveData;
import com.example.mytestapp.db.firebase.PollLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
//The class is used to transfer data from DAO to ViewModel

public class PollRepository {

    private static PollRepository instance;

    private PollRepository() {}

    public static PollRepository getInstance() {
        if (instance == null) {
            synchronized (PollRepository.class) {
                if (instance == null) {
                    instance = new PollRepository();
                }
            }
        }
        return instance;
    }






    public LiveData<Poll> getPoll(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id);
        return new PollLiveData(reference);
    }

    public LiveData<List<Poll>> getActivePolls() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("votes");
            return new PollListLiveData(reference);
    }


 //FAIT  INSERT, UPDATE ET DELETE


    public String insert(final Poll poll, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(poll.getUser_id());
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(key)
                .setValue(poll, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        return key;
    }


    public void updatePoll(final Poll poll, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(poll.getPid())
                .updateChildren(poll.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void deletePoll(final Poll poll, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(poll.getPid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
