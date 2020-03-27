package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreateUser;
import com.example.mytestapp.db.async.DeleteUser;
import com.example.mytestapp.db.async.UpdateUser;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.util.OnAsyncEventListener;

import java.util.List;

public class PollRepository {

    private static PollRepository instance;

    private PollRepository() {}

    public static PollRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new PollRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Poll> getLastPoll( Context context) {
        return AppDatabase.getInstance(context).pollDao().getLastPoll();
    }

    public LiveData<List<User>> getAllUsers(Context context) {
        return AppDatabase.getInstance(context).userDao().getAllUsers();
    }

    public void insertUser(final User user, OnAsyncEventListener callback, Context context) {
        new CreateUser(context, callback).execute(user);
    }

    public void updateUser(final User user, OnAsyncEventListener callback, Context context) {
        new UpdateUser(context, callback).execute(user);
    }

    public void deleteUser(final User user, OnAsyncEventListener callback, Context context) {
        new DeleteUser(context, callback).execute(user);
    }
}
