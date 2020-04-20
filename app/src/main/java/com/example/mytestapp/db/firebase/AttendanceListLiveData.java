package com.example.mytestapp.db.firebase;

import android.util.Log;

import com.example.mytestapp.db.entities.Attendance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class AttendanceListLiveData extends LiveData<List<Attendance>> {

    private static final String TAG = "ClientAccountsLiveData";

    private final DatabaseReference reference;
    private final String idMeeting;
    private final MyValueEventListener listener = new MyValueEventListener();

    public AttendanceListLiveData(String idMeeting,DatabaseReference ref) {
        reference = ref;
        this.idMeeting = idMeeting;
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
            setValue(toAttendanceList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Attendance> toAttendanceList(DataSnapshot snapshot) {
        List<Attendance> attendances = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if(childSnapshot.hasChild("meeting_id"))
                if( childSnapshot.child("meeting_id").getValue().equals(idMeeting)) {
                    Attendance entity = childSnapshot.getValue(Attendance.class);
                    entity.setAid(childSnapshot.getKey());
                    attendances.add(entity);
                }
        }
        return attendances;
    }
}