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
//The DAO class is used to create method to interact with the Database in SQL


@Dao
public interface MeetingDao {

    //The method get a list of Meeting from the Database if the current date is bigger
    @Query("SELECT * FROM Meeting WHERE day_meeting > strftime('%s','now')")
    LiveData<List<Meeting>> getActiveMeetings();
    //The method get a list of Meeting related to the user
    @Query("SELECT * FROM Meeting WHERE user_id = :id")
    LiveData<List<Meeting>> getMyMeetings(int id);
    //The method get a list of Meeting from the Database related to one meeting
    @Query("SELECT * FROM Meeting WHERE mid = :id")
    LiveData<Meeting> getMeeting(int id);
    //The method insert a Meeting in the Database
    @Insert
    void insertMeeting(Meeting meeting) throws SQLiteConstraintException;
    //The method update a Meeting in the Database
    @Update
    void updateMeeting(Meeting meeting);
    //The method delete a Meeting in the Database
    @Delete
    void deleteMeeting(Meeting meeting);

}

