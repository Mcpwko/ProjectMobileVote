package com.example.mytestapp.db.repository;

//The repository class is called by the viewModel. It creates the connection with the database.

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Vote;
import com.example.mytestapp.db.firebase.VoteListLiveData;
import com.example.mytestapp.db.firebase.VoteMyListLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class VoteRepository {

    private static VoteRepository instance;

    private VoteRepository() {}
    //The getInstance method checks to see if an object of that class already exists in the program.
    // It returns null if an object exists.
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


    //This method is used to get a list of Votes from the database
    public LiveData<List<Vote>> getVote(final String idPoll, String idUser) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("answers");
        return new VoteListLiveData(idPoll, idUser, reference);
    }


    //This method is used to get a list of Votes by a Poll
    public LiveData<List<Vote>> getVotesByPoll(final String idPoll){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("answers");
        return new VoteMyListLiveData(idPoll, reference);
    }

    //This method is used to insert a Vote into the Database
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

    //This method is used to delete a Vote in the database
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
