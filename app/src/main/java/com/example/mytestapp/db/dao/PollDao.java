package com.example.mytestapp.db.dao;


import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytestapp.db.entities.Poll;

import java.util.List;

@Dao
public interface PollDao {

    @Query("SELECT * FROM Poll")
    List<Poll> getAllPolls();

    @Query("SELECT * from Poll order by pid DESC limit 1")
    LiveData<Poll> getLastPoll();

    @Query("SELECT * FROM Poll WHERE pid = :id")
    LiveData<Poll> getById(int id);

    @Insert
    void insertPoll(Poll poll) throws SQLiteConstraintException;

    @Update
    void updatePoll(Poll poll);

    @Delete
    void deletePoll(Poll poll);

}
