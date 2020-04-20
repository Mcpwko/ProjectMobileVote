package com.example.mytestapp.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.PossibleAnswers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PossibleAnswerListLiveData extends LiveData<List<PossibleAnswers>> {

    private static final String TAG = "MeetingListLiveData";
    private String idPoll;

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public PossibleAnswerListLiveData(String idPoll,DatabaseReference ref) {
        reference = ref;
        this.idPoll = idPoll;
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
            setValue(getPossibleAnswers(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<PossibleAnswers> getPossibleAnswers(DataSnapshot snapshot) {
        List<PossibleAnswers> accounts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if(childSnapshot.child("pollid").getValue().equals(idPoll)) {
                PossibleAnswers entity = childSnapshot.getValue(PossibleAnswers.class);
                entity.setPaid(childSnapshot.getKey());
                accounts.add(entity);
            }
        }
        return accounts;
    }
}