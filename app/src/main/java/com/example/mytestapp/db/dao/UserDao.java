package com.example.mytestapp.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytestapp.db.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE uid IN(:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
    User findbyName(String first, String last);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void delete(User user);

}
