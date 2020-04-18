package com.example.mytestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

salut

//We used this class as a welcome screen

public class LoadingScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCE = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";

    //This is the time that the screen will be displayed
    private static int SPLASH_TIME = 1000;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);


        //We check if the night mode is activated
        checkNightModeActivated();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run (){
                Intent homeIntent = new Intent(LoadingScreen.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME);
    }


    //This method if the night mode is activated and store it into a sharedPreference to keep it
    //during all the sessions of the user on the application
    public void checkNightModeActivated() {
        sharedPreferences = getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
