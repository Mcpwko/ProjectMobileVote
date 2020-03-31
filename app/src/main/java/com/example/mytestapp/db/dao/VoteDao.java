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


@Dao
public interface VoteDao {

    @Query("SELECT * FROM vote WHERE user_id LIKE(:idUser) AND poll_id LIKE(:idPoll)")
    LiveData<List<Vote>> getVote(int idUser, int idPoll);

    @Query("SELECT * FROM vote WHERE poll_id LIKE(:idPoll)")
    LiveData<List<Vote>> getVoteByPoll(int idPoll);

    @Insert
    void insertVote(Vote vote) throws SQLiteConstraintException;

    @Update
    void updateVote(Vote vote);

    @Delete
    void deleteVote(Vote vote);

}
