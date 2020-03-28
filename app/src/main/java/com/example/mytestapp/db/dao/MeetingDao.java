package com.example.mytestapp.db.dao;


import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytestapp.db.entities.Meeting;

import java.util.List;


@Dao
public interface MeetingDao {

    @Query("SELECT * FROM Meeting")
    List<Meeting> getAllMeetings();

    @Query("SELECT * FROM Meeting WHERE day_meeting > strftime('%s','now')")
    LiveData<List<Meeting>> getActiveMeetings();

    @Query("SELECT * FROM Meeting WHERE user_id = :id")
    LiveData<List<Meeting>> getMyMeetings(int id);

    @Query("SELECT * FROM Meeting WHERE mid = :id")
    LiveData<Meeting> getMeeting(int id);


    @Insert
    void insertMeeting(Meeting meeting) throws SQLiteConstraintException;

    @Update
    void updateMeeting(Meeting meeting);

    @Delete
    void deleteMeeting(Meeting meeting);

}

