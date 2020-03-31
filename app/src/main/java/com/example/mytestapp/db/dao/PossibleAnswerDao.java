package com.example.mytestapp.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mytestapp.db.entities.PossibleAnswers;

import java.util.List;

//The DAO class is used to create method to interact with the Database in SQL

@Dao
public interface PossibleAnswerDao {


    //The method get a list of PossibleAnswers related to the pollId
    @Query("SELECT * FROM PossibleAnswers WHERE pollid = :id")
    LiveData<List<PossibleAnswers>> getPossibleAnswersByPoll(int id);
    //The method insert a Poll in the Database
    @Insert
    void insertPossibleAnswer(PossibleAnswers possibleAnswers) throws SQLiteConstraintException;
    //The method delete a Poll in the Database
    @Delete
    void deletePossibleAnswer(PossibleAnswers possibleAnswers);
}
