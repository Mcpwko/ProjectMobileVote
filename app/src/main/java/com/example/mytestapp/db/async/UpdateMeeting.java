package com.example.mytestapp.db.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.util.OnAsyncEventListener;
//This class is used to update a Meeting in the Database via an AppDatabase Object

public class UpdateMeeting extends AsyncTask<Meeting, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;
    //This method is used to update a Meeting using the getInstance method
    public UpdateMeeting(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }
    //This method is used to perform a computation on a background thread.This will normally run
    // on a background thread
    @Override
    protected Void doInBackground(Meeting... params) {
        try {
            for (Meeting meeting : params)
                database.meetingDao().updateMeeting(meeting);
        } catch (Exception e) {
            exception = e;
        }
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
