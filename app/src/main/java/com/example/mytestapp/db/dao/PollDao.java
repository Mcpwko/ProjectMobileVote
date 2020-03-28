package com.example.mytestapp.db.dao;


import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;

import java.util.List;

@Dao
public interface PollDao {

    @Query("SELECT * FROM Poll order by deadline_poll DESC")
    List<Poll> getAllPolls();

    @Query("SELECT * FROM Poll WHERE deadline_poll > strftime('%s','now')")
    LiveData<List<Poll>> getActivePolls();

    @Query("SELECT * from Poll order by pid DESC limit 1")
    LiveData<Poll> getLastPoll();

    @Query("SELECT * FROM Poll WHERE user_id = :id")
    LiveData<List<Poll>> getMyPolls(int id);


    @Query("SELECT * FROM Poll WHERE pid = :id")
    LiveData<Poll> getPoll(int id);

    @Insert
    void insertPoll(Poll poll) throws SQLiteConstraintException;

    @Update
    void updatePoll(Poll poll);

    @Delete
    void deletePoll(Poll poll);

}
