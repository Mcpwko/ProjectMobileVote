package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreateMeeting;
import com.example.mytestapp.db.async.DeleteMeeting;
import com.example.mytestapp.db.async.UpdateMeeting;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.util.OnAsyncEventListener;

import java.util.List;

public class MeetingRepository {

    private static MeetingRepository instance;

    private MeetingRepository() {}

    public static MeetingRepository getInstance() {
        if (instance == null) {
            synchronized (MeetingRepository.class) {
                if (instance == null) {
                    instance = new MeetingRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<List<Meeting>> getActiveMeeting(Context context) {
        return AppDatabase.getInstance(context).meetingDao().getActiveMeetings();
    }

    public LiveData<List<Meeting>> getMyMeetings (int id, Context context) {
        return AppDatabase.getInstance(context).meetingDao().getMyMeetings(id);
    }

    public LiveData<Meeting> getMeeting (int id, Context context) {
        return AppDatabase.getInstance(context).meetingDao().getMeeting(id);
    }


    public void insertMeeting(final Meeting meeting, OnAsyncEventListener callback, Context context) {
        new CreateMeeting(context, callback).execute(meeting);
    }

    public void updateMeeting(final Meeting meeting, OnAsyncEventListener callback, Context context) {
        new UpdateMeeting(context, callback).execute(meeting);
    }

    public void deleteMeeting(final Meeting meeting, OnAsyncEventListener callback, Context context) {
        new DeleteMeeting(context, callback).execute(meeting);
    }
}