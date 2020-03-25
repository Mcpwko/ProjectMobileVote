package com.example.mytestapp.db.dao;


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
    void insertPoll(Meeting meeting);

    @Update
    void updatePoll(Meeting meeting);

    @Delete
    void deletePoll(Meeting meeting);

}

