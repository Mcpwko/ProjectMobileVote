package com.example.mytestapp.db.repository;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.firebase.AttendanceListLiveData;
import com.example.mytestapp.db.firebase.AttendanceLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    public LiveData<Attendance> getAttendance(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id);
        return new AttendanceLiveData(reference);
    }

    // A voir comment faire
    /*public LiveData<Attendance> getAttendanceById (int id, Context context) {
        return AppDatabase.getInstance(context).attendanceDao().getAttendanceById(id);
    }*/


    public LiveData<List<Attendance>> getAttendances() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("votes");
        return new AttendanceListLiveData(reference);
    }


    public void insertAttendance(final Attendance attendance, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("votes").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(id)
                .setValue(attendance, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    /*public void deleteAttendance(final Attendance attendance, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(attendance.getAid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }*/


}