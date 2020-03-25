package com.example.mytestapp.db.dao;


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

    @Insert
    void insertPoll(Poll poll);

    @Update
    void updatePoll(Poll poll);

    @Delete
    void deletePoll(Poll poll);

}
