package com.example.mytestapp.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mytestapp.adapter.Converters;
import com.example.mytestapp.db.dao.AttendanceDao;
import com.example.mytestapp.db.dao.MeetingDao;
import com.example.mytestapp.db.dao.PollDao;
import com.example.mytestapp.db.dao.PossibleAnswerDao;
import com.example.mytestapp.db.dao.UserDao;
import com.example.mytestapp.db.dao.VoteDao;
import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.entities.Vote;

import java.util.concurrent.Executors;

//All the DAO classes are declared in this class in order to create the Room Database


//Below are all our entities
@Database(entities = {Attendance.class,Meeting.class,Poll.class,PossibleAnswers.class,User.class,Vote.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    private static final String DATABASE_NAME = "VoteDatabase";

    public abstract MeetingDao meetingDao();
    public abstract PollDao pollDao();
    public abstract UserDao userDao();
    public abstract PossibleAnswerDao possibleAnswerDao();
    public abstract AttendanceDao attendanceDao();
    public abstract VoteDao voteDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    //used to build the database
                    instance = buildDatabase(context.getApplicationContext());
                    //Used to update the database
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    //The method below build the databse by setting up the database configuration and creating
    //a new instance of the database.
    //The SQLite database is only created when it's accessed for the first time.
    private static AppDatabase buildDatabase(final Context appContext) {
        //Used to communicate the database state through the console
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            DatabaseInitializer.populateDatabase(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }



    //Check whether the database already exists and expose it via {@link #getDatabaseCreated()
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}
