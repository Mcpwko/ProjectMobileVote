package com.example.mytestapp.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytestapp.db.entities.PossibleAnswers;

import java.util.List;


@Dao
public interface PossibleAnswerDao {
    @Query("SELECT * FROM PossibleAnswers")
    List<PossibleAnswers> getAllPossibleAnswers();

    @Query("SELECT * FROM PossibleAnswers WHERE pollid = :id")
    LiveData<List<PossibleAnswers>> getPossibleAnswersByPoll(int id);
    @Insert
    void insertPossibleAnswer(PossibleAnswers possibleAnswers) throws SQLiteConstraintException;

    @Update
    void updatePossibleAnswer(PossibleAnswers possibleAnswers);

    @Delete
    void deletePossibleAnswer(PossibleAnswers possibleAnswers);
}
