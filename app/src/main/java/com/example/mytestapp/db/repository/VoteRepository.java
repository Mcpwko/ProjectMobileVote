package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreateVote;
import com.example.mytestapp.db.entities.Poll2;
import com.example.mytestapp.db.entities.Vote;
import com.example.mytestapp.db.entities.Vote2;
import com.example.mytestapp.db.firebase.UserLiveData;
import com.example.mytestapp.db.firebase.VoteListLiveData;
import com.example.mytestapp.db.firebase.VoteLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
//The class is used to transfer data from DAO to ViewModel

public class VoteRepository {

    private static VoteRepository instance;

    private VoteRepository() {}

    public static VoteRepository getInstance() {
        if (instance == null) {
            synchronized (VoteRepository.class) {
                if (instance == null) {
                    instance = new VoteRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<List<Vote2>> getVote() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes");
        return new VoteListLiveData(reference);
    }

    // A voir comment faire
    public LiveData<List<Vote>> getVotesByPoll(int idPoll, Context context){
        return AppDatabase.getInstance(context).voteDao().getVoteByPoll(idPoll);
    }

    public void insertVote(final Vote2 vote, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("votes").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id)
                .setValue(vote, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
