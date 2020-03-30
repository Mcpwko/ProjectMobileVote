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


    @Query("SELECT * FROM Attendance WHERE meeting_id = :idMeeting AND user_id = :idUser")
    LiveData<Attendance> getAttendance(int idUser,int idMeeting);


    @Insert
    void insertAttendance(Attendance attendance) throws SQLiteConstraintException;

    @Update
    void updateAttendance(Attendance attendance);

    @Delete
    void deleteAttendance(Attendance attendance);

}
