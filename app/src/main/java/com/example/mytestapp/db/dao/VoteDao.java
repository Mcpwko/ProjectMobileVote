package com.example.mytestapp.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.mytestapp.db.entities.Vote;

import java.util.List;
//The DAO class is used to create method to interact with the Database in SQL

@Dao
public interface VoteDao {

    //The method get the votes of a user related to a Poll
    @Query("SELECT * FROM vote WHERE user_id LIKE(:idUser) AND poll_id LIKE(:idPoll)")
    LiveData<List<Vote>> getVote(int idUser, int idPoll);
    //The method get the votes by a Poll
    @Query("SELECT * FROM vote WHERE poll_id LIKE(:idPoll)")
    LiveData<List<Vote>> getVoteByPoll(int idPoll);
    //The method insert a Vote in the Database
    @Insert
    void insertVote(Vote vote) throws SQLiteConstraintException;
    //The method update a Vote in the Database
    @Update
    void updateVote(Vote vote);
    //The method delete a Vote in the Database
    @Delete
    void deleteVote(Vote vote);

}
