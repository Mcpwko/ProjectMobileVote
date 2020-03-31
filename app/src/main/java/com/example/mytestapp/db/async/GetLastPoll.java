package com.example.mytestapp.db.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.util.OnAsyncEventListener;
//This class is used to get the last poll in the Database via an AppDatabase Object

public class GetLastPoll extends AsyncTask<Poll, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;
    //This method is used to get the LastPoll using the getInstance method
    public GetLastPoll(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }
    //This method is used to perform a computation on a background thread.This will normally run
    // on a background thread
    @Override
    protected Void doInBackground(Poll... polls) {

        database.pollDao().getLastPoll();
        return null;

    }
    //This method is used to check what must be done after the execution
    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
