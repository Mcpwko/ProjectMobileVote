package com.example.mytestapp.db.repository;
//The repository class is called by the viewModel. It creates the connection with the database.

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.firebase.UserLiveData;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserRepository {

    private static final String TAG = "UserRepository";

    private static UserRepository instance;

    private UserRepository() {
    }
    //The getInstance method checks to see if an object of that class already exists in the program.
    // It returns null if an object exists.
    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    //This method is used to log in the app by using the FirebaseAuth class

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    //This method is used to get the User from the database
    public LiveData<User> getUser(final String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId);
        return new UserLiveData(reference);
    }

    //This method is used to register in the app by using the FirebaseAuth class
    public void register(final User user, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(user, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }

    //This method is used to insert a user into the database
    private void insert(final User user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                        Log.d(TAG, "Rollback successful: User account deleted");
                                    } else {
                                        callback.onFailure(task.getException());
                                        Log.d(TAG, "Rollback failed: signInWithEmail:failure",
                                                task.getException());
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    //This method is used to update a user in the database
    public void update(final User user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getUid())
                .updateChildren(user.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(user.getPassword())
                .addOnFailureListener(
                        e -> Log.d(TAG, "updatePassword failure!", e)
                );
    }

    //This method is used to delete a user in the database
    public void delete(final User user, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getUid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
