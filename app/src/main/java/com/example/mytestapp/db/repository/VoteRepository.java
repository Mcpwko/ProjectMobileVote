package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreateVote;
import com.example.mytestapp.db.entities.Vote;
import com.example.mytestapp.util.OnAsyncEventListener;

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

    //The methods below are used to get datas from the DAO


    public LiveData<List<Vote>> getVote(int idUser, int idPoll,Context context) {
        return AppDatabase.getInstance(context).voteDao().getVote(idUser, idPoll);
    }

    public LiveData<List<Vote>> getVotesByPoll(int idPoll, Context context){
        return AppDatabase.getInstance(context).voteDao().getVoteByPoll(idPoll);
    }

    public void insertVote(final Vote vote, OnAsyncEventListener callback, Context context) {
        new CreateVote(context, callback).execute(vote);
    }
}
