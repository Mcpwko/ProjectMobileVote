package com.example.mytestapp.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.mytestapp.db.entities.Attendance;

import java.util.List;

//The DAO class is used to create method to interact with the Database in SQL

@Dao
public interface AttendanceDao {


    //The method get an Attendance from the Database related to the IdMeeting and the IdUser
    @Query("SELECT * FROM Attendance WHERE meeting_id = :idMeeting AND user_id = :idUser")
    LiveData<Attendance> getAttendance(int idUser,int idMeeting);
    //The method get an Attendance from the Database related to the IdAnswer
    @Query("SELECT * FROM Attendance WHERE aid = :id")
    LiveData<Attendance> getAttendanceById(int id);
    //The method get a list of Attendance from the Database related to the IdMeeting
    @Query("SELECT * FROM Attendance WHERE meeting_id = :idMeeting")
    LiveData<List<Attendance>> getAttendances(int idMeeting);
    //The method insert an Attendance in the Database
    @Insert
    void insertAttendance(Attendance attendance) throws SQLiteConstraintException;
    //The method delete an Attendance from the Database
    @Delete
    void deleteAttendance(Attendance attendance);

}
