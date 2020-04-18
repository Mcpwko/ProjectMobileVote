package com.example.mytestapp.db.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreatePoll;
import com.example.mytestapp.db.async.DeletePoll;
import com.example.mytestapp.db.async.UpdatePoll;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.Poll2;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.firebase.PollLiveData;
import com.example.mytestapp.db.firebase.UserLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
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


    // A voir comment faire
    public LiveData<Poll> getLastPoll( Context context) {
        return AppDatabase.getInstance(context).pollDao().getLastPoll();
    }

    // A voir comment faire
    public LiveData<List<Poll>> getMyPolls (int id, Context context) {
        return AppDatabase.getInstance(context).pollDao().getMyPolls(id);
    }

    //FAIT
    public LiveData<Poll> getPoll(final String idPoll) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("polls")
                .child(idPoll);
        PollLiveData pl = new PollLiveData(reference);
        return (LiveData) pl;
    }

    // A voir comment faire
    public LiveData<List<Poll>> getActivePolls(Context context) {
        return AppDatabase.getInstance(context).pollDao().getActivePolls();
    }


 //FAIT  INSERT, UPDATE ET DELETE


    public void insertPoll(final Poll2 poll, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("votes").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(id)
                .setValue(poll, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void updatePoll(final Poll2 poll, final OnAsyncEventListener callback) {
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


    public void deletePoll(final Poll2 poll, final OnAsyncEventListener callback) {
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
