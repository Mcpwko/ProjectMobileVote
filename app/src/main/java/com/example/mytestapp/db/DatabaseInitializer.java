package com.example.mytestapp.db;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mytestapp.db.entities.Address1;
import com.example.mytestapp.db.entities.User;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addUser(final AppDatabase db, final String lastName, final String firstName,
                                final String birthdate, final Address1 address, final String phoneNumber,
                                final String email, final String password) {
        User user = new User(firstName, lastName,birthdate,address,phoneNumber,email,password);
        db.userDao().insertUser(user);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.userDao().deleteAll();

        addUser(db, "Coppey","Kevin","24.07.1997",new Address1("Avenue Rossfeld 21","Sierre"),
                "0793989228","kevin.coppey@hes.ch","password");
        addUser(db, "Puglisi","Mickael","19.01.1994",new Address1("Chemin du Vieux Canal 2","Sion"),
                "0788303720","mickael.puglisi@hes.ch","password");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
