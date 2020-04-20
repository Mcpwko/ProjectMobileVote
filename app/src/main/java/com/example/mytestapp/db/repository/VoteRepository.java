package com.example.mytestapp.db.repository;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Vote;
import com.example.mytestapp.db.firebase.VoteListLiveData;
import com.example.mytestapp.db.firebase.VoteMyListLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DataSnapshot;
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


    public LiveData<List<Vote>> getVote(final String idPoll, String idUser) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("answers");
        return new VoteListLiveData(idPoll, idUser, reference);
    }


    public LiveData<List<Vote>> getVotesByPoll(final String idPoll){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("answers");
        return new VoteMyListLiveData(idPoll, reference);
    }

    public void insertVote(final Vote vote, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(vote.getPoll_id());
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("answers")
                .child(key)
                .setValue(vote, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void deleteVote(final Vote vote, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("answers")
                .child(vote.getVid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


}
