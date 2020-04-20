package com.example.mytestapp.db.repository;

//The class is used to transfer data from DAO to ViewModel

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.db.firebase.PossibleAnswerListLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PossibleAnswersRepository {

    private static PossibleAnswersRepository instance;

    private PossibleAnswersRepository() {}

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
    //The methods below are used to get datas from the DAO

    //A voir comment faire
    /*public LiveData<List<PossibleAnswers>> getPossibleAnswersByPoll (int id, Context context) {
        return AppDatabase.getInstance(context).possibleAnswerDao().getPossibleAnswersByPoll(id);
    }*/

    public LiveData<List<PossibleAnswers>> getPossibleAnswersByPoll(final String idPoll) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("possibleAnswers");
        return new PossibleAnswerListLiveData(idPoll,reference);
    }




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



}
