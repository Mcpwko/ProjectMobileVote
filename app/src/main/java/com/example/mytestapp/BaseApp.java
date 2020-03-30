package com.example.mytestapp;

import android.app.Application;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public PollRepository getPollRepository() {
        return PollRepository.getInstance();
    }

    public MeetingRepository getMeetingRepository() {
        return MeetingRepository.getInstance();
    }
}
