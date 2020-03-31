package com.example.mytestapp.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytestapp.db.entities.User;

import java.util.List;
//The DAO class is used to create method to interact with the Database in SQL

@Dao
public interface UserDao {
    //The method get all the Users from the Database
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();
    //Get all the Users by there Email
    @Query("SELECT * FROM user WHERE email LIKE(:email)")
    LiveData<User> getUserByEmail(String email);
    //The method get all the Users by there id
    @Query("SELECT * FROM user WHERE uid LIKE(:id)")
    LiveData<User> getUserById(int id);
    //The method insert a User in the Database
    @Insert
    void insertUser(User user) throws SQLiteConstraintException ;
    //The method update a User in the Database
    @Update
    void updateUser(User user);
    //The method delete a User in the Database
    @Delete
    void deleteUser(User user);
    //The method is used to clean up the database in order to create default entries (DatabaseInitializer.java)
    @Query("DELETE FROM user")
    void deleteAll();

}
