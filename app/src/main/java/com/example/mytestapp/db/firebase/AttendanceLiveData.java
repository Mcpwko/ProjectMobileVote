package com.example.mytestapp.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Attendance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendanceLiveData extends LiveData<Attendance> {
    private static final String TAG = "MeetingLiveData";

    private final DatabaseReference reference;
    private final String idMeeting;
    private final String idUser;
    private final AttendanceLiveData.MyValueEventListener listener = new AttendanceLiveData.MyValueEventListener();

    public AttendanceLiveData(String idMeeting, String idUser,DatabaseReference ref) {
        this.reference = ref;
        this.idMeeting = idMeeting;
        this.idUser = idUser;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
            setValue(getAttendance(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private Attendance getAttendance(DataSnapshot snapshot) {
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if(childSnapshot.hasChild("meeting_id"))
            if( childSnapshot.child("user_id").getValue().equals(idUser) && childSnapshot.child("meeting_id").getValue().equals(idMeeting)) {
                Attendance entity = childSnapshot.getValue(Attendance.class);
                entity.setAid(childSnapshot.getKey());
                return entity;
            }
        }
        return null;
    }
}
