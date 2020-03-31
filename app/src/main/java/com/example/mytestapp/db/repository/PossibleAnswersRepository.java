package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.entities.PossibleAnswers;

import java.util.List;
//The class is used to transfer data from DAO to ViewModel

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

    public LiveData<List<PossibleAnswers>> getPossibleAnswersByPoll (int id, Context context) {
        return AppDatabase.getInstance(context).possibleAnswerDao().getPossibleAnswersByPoll(id);
    }

}
