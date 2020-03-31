package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreateAttendance;
import com.example.mytestapp.db.async.DeleteAttendance;
import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.util.OnAsyncEventListener;

import java.util.List;

//The class is used to transfer data from DAO to ViewModel

public class AttendanceRepository {

    private static AttendanceRepository instance;

    private AttendanceRepository() {}

    //The method getInstance is used in the ASYNC to get data
    public static AttendanceRepository getInstance() {
        if (instance == null) {
            synchronized (AttendanceRepository.class) {
                if (instance == null) {
                    instance = new AttendanceRepository();
                }
            }
        }
        return instance;
    }

    //The methods below are used to get datas from the DAO


    public LiveData<Attendance> getAttendance (int idUser,int idMeeting, Context context) {
        return AppDatabase.getInstance(context).attendanceDao().getAttendance(idUser,idMeeting);
    }

    public LiveData<Attendance> getAttendanceById (int id, Context context) {
        return AppDatabase.getInstance(context).attendanceDao().getAttendanceById(id);
    }

    public LiveData<List<Attendance>> getAttendances(int idMeeting,Context context){
        return AppDatabase.getInstance(context).attendanceDao().getAttendances(idMeeting);
    }



    public void insertAttendance(final Attendance attendance, OnAsyncEventListener callback, Context context) {
        new CreateAttendance(context, callback).execute(attendance);
    }

    public void deleteAttendance(final Attendance attendance, OnAsyncEventListener callback, Context context){
        new DeleteAttendance(context, callback).execute(attendance);
    }

}