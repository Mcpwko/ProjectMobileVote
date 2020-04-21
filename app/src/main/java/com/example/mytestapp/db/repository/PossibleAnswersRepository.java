package com.example.mytestapp.db.repository;

//The repository class is called by the viewModel. It creates the connection with the database.

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.db.firebase.PossibleAnswerListLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PossibleAnswersRepository {

    private static PossibleAnswersRepository instance;

    private PossibleAnswersRepository() {}
    //The getInstance method checks to see if an object of that class already exists in the program.
    // It returns null if an object exists.
    public static PossibleAnswersRepository getInstance() {
        if (instance == null) {
            synchronized (PossibleAnswersRepository.class) {
                if (instance == null) {
                    instance = new PossibleAnswersRepository();
                }
            }
        }
        return instance;
    }

    //This method is used to get a list of all the PossibleAnswers from the database

    public LiveData<List<PossibleAnswers>> getPossibleAnswersByPoll(final String idPoll) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("possibleAnswers");
        return new PossibleAnswerListLiveData(idPoll,reference);
    }

    //This method is used to insert a PossibleAnswer into the database

    public void insert(final PossibleAnswers possibleAnswers, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(possibleAnswers.getPollid());
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("possibleAnswers")
                .child(key)
                .setValue(possibleAnswers, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    //This method is used to delete a PossibleAnswer in the database

    public void deletePossibleAnswers(final PossibleAnswers possibleAnswers, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("possibleAnswers")
                .child(possibleAnswers.getPaid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }



}
