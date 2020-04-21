package com.example.mytestapp.db.repository;

//The repository class is called by the viewModel. It creates the connection with the database.

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.firebase.AttendanceListLiveData;
import com.example.mytestapp.db.firebase.AttendanceLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class AttendanceRepository {

    private static AttendanceRepository instance;

    private AttendanceRepository() {}

    //The getInstance method checks to see if an object of that class already exists in the program.
    // It returns null if an object exists.

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

    //This method is used to get an attendance from the database

    public LiveData<Attendance> getAttendance(final String idMeeting, final String idUser) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("answers");
        return new AttendanceLiveData(idMeeting, idUser, reference);
    }


    //This method is used to get a list of attendances from the database

    public LiveData<List<Attendance>> getAttendances(String idMeeting) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("answers");
        return new AttendanceListLiveData(idMeeting,reference);
    }


    //This method is used to insert an attendance into the database

    public void insertAttendance(final Attendance attendance, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("votes").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("answers")
                .child(id)
                .setValue(attendance, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    //This method is used to delete an attendance from the database

    public void deleteAttendance(final Attendance attendance, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("answers")
                .child(attendance.getAid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


}