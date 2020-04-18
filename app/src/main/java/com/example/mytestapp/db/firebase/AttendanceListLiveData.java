package com.example.mytestapp.db.firebase;

import android.util.Log;

import com.example.mytestapp.db.entities.Attendance2;
import com.example.mytestapp.db.entities.Vote2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class AttendanceListLiveData extends LiveData<List<Attendance2>> {

    private static final String TAG = "ClientAccountsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public AttendanceListLiveData(DatabaseReference ref) {
        reference = ref;
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
            setValue(toAttendanceList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Attendance2> toAttendanceList(DataSnapshot snapshot) {
        List<Attendance2> attendances = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Attendance2 entity = childSnapshot.getValue(Attendance2.class);
            entity.setAid(childSnapshot.getKey());
            attendances.add(entity);
        }
        return attendances;
    }
}