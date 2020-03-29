package com.example.mytestapp.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.mytestapp.db.entities.Attendance;

import java.util.List;

@Dao
public interface AttendanceDao {


    @Query("SELECT * FROM Attendance WHERE meeting_id = :id")
    LiveData<Attendance> getAttendance(int id);


    @Insert
    void insertAttendance(Attendance attendance) throws SQLiteConstraintException;

    @Update
    void updateAttendance(Attendance attendance);

    @Delete
    void deleteAttendance(Attendance attendance);

}
