package com.example.mytestapp.db.dao;


import android.database.sqlite.SQLiteConstraintException;

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

    @Insert
    void insertMeeting(Meeting meeting) throws SQLiteConstraintException;

    @Update
    void updateMeeting(Meeting meeting);

    @Delete
    void deleteMeeting(Meeting meeting);

}

