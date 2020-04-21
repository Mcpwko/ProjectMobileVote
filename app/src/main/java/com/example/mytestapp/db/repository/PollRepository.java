package com.example.mytestapp.db.repository;

//The repository class is called by the viewModel. It creates the connection with the database.

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Poll;
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
    //The getInstance method checks to see if an object of that class already exists in the program.
    // It returns null if an object exists.
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

    //This method is used to get a poll from the database

    public LiveData<Poll> getPoll(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id);
        return new PollLiveData(reference);
    }

    //This method is used to get a list of Polls from the database

    public LiveData<List<Poll>> getActivePolls() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("votes");
            return new PollListLiveData(reference);
    }



    //This method is used to insert a Poll into the database

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

    //This method is used to update a Poll in the database

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

    //This method is used to delete a Poll in the database

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
