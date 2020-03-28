package com.example.mytestapp.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreateUser;
import com.example.mytestapp.db.async.DeleteUser;
import com.example.mytestapp.db.async.UpdateUser;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.util.OnAsyncEventListener;

import java.util.List;

public class UserRepository {

    private static UserRepository instance;

    private UserRepository() {}

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

    public LiveData<User> getUser(final String email, Context context) {
        return AppDatabase.getInstance(context).userDao().getUserByEmail(email);
    }

    public LiveData<User> getUserById(final int id, Context context) {
        return AppDatabase.getInstance(context).userDao().getUserById(id);
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
