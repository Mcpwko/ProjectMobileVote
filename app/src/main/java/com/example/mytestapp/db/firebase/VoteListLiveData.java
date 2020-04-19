package com.example.mytestapp.db.firebase;

import android.util.Log;

import com.example.mytestapp.db.entities.Vote;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class VoteListLiveData extends LiveData<List<Vote>> {

    private static final String TAG = "ClientAccountsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public VoteListLiveData(DatabaseReference ref) {
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
            setValue(toVoteList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Vote> toVoteList(DataSnapshot snapshot) {
        List<Vote> votes = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Vote entity = childSnapshot.getValue(Vote.class);
            entity.setVid(childSnapshot.getKey());
            votes.add(entity);
        }
        return votes;
    }
}