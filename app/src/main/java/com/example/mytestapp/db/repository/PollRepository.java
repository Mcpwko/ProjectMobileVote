package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreatePoll;
import com.example.mytestapp.db.async.DeletePoll;
import com.example.mytestapp.db.async.UpdatePoll;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.util.OnAsyncEventListener;

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
    //The methods below are used to get datas from the DAO

    public LiveData<Poll> getLastPoll( Context context) {
        return AppDatabase.getInstance(context).pollDao().getLastPoll();
    }

    public LiveData<List<Poll>> getMyPolls (int id, Context context) {
        return AppDatabase.getInstance(context).pollDao().getMyPolls(id);
    }

    public LiveData<Poll> getPoll (int id, Context context) {
        return AppDatabase.getInstance(context).pollDao().getPoll(id);
    }

    public LiveData<List<Poll>> getActivePolls(Context context) {
        return AppDatabase.getInstance(context).pollDao().getActivePolls();
    }


    public void insertPoll(final Poll poll, OnAsyncEventListener callback, Context context) {
        new CreatePoll(context, callback).execute(poll);
    }

    public void updatePoll(final Poll poll, OnAsyncEventListener callback, Context context) {
        new UpdatePoll(context, callback).execute(poll);
    }

    public void deletePoll(final Poll poll, OnAsyncEventListener callback, Context context) {
        new DeletePoll(context, callback).execute(poll);
    }
}
