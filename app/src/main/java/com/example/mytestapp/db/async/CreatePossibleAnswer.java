package com.example.mytestapp.db.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.util.OnAsyncEventListener;

public class CreatePossibleAnswer extends AsyncTask<PossibleAnswers, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreatePossibleAnswer(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(PossibleAnswers... params) {
        try {
            for (PossibleAnswers possibleAnswers : params)
                database.possibleAnswerDao().insertPossibleAnswer(possibleAnswers);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

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
