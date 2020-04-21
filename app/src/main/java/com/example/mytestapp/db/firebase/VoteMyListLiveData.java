package com.example.mytestapp.db.firebase;
//The LiveData is an observable data holder class. It respects the lifecycle of the other app
//components like the fragments we used. It only updates component observers that are in an active
//state
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.Vote;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VoteMyListLiveData extends LiveData<List<Vote>> {

    private static final String TAG = "VoteListLiveData";

    private final DatabaseReference reference;
    private final String idPoll;
    private final MyValueEventListener listener = new MyValueEventListener();

    public VoteMyListLiveData(String idPoll, DatabaseReference ref) {
        reference = ref;
        this.idPoll = idPoll;
    }
    //This method is called when the LiveData object has an active observer.

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }
    //This method is called when the LiveData object doesn't have any active observers.

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }
    // Used to receive events about data changes at a location

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
            setValue(toVoteList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
    //Here we go through all our list in the database (each nodes) of a specific entity

    private List<Vote> toVoteList(DataSnapshot snapshot) {
        List<Vote> votes = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if(childSnapshot.hasChild("poll_id"))
                if(childSnapshot.child("poll_id").getValue().equals(idPoll)) {
                    Vote entity = childSnapshot.getValue(Vote.class);
                    entity.setVid(childSnapshot.getKey());
                    votes.add(entity);
                }
        }
        return votes;
    }
}