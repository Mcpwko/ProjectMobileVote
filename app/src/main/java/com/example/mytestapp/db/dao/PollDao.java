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
//The DAO class is used to create method to interact with the Database in SQL

@Dao
public interface PollDao {


    //The method get all Polls from the Database ordered by the deadline
    @Query("SELECT * FROM Poll WHERE deadline_poll > strftime('%s','now')")
    LiveData<List<Poll>> getActivePolls();
    //The method get the last Poll from the Database
    @Query("SELECT * from Poll order by pid DESC limit 1")
    LiveData<Poll> getLastPoll();
    //The method get all the Poll of a user
    @Query("SELECT * FROM Poll WHERE user_id = :id")
    LiveData<List<Poll>> getMyPolls(int id);
    //The method get a Poll from the Database
    @Query("SELECT * FROM Poll WHERE pid = :id")
    LiveData<Poll> getPoll(int id);
    //The method insert a Poll in the Database
    @Insert
    void insertPoll(Poll poll) throws SQLiteConstraintException;
    //The method update a Poll in the Database
    @Update
    void updatePoll(Poll poll);
    //The method delete a Poll in the Database
    @Delete
    void deletePoll(Poll poll);

}
