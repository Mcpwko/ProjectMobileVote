package com.example.mytestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationView;

public class LoadingScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCE = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";

    private static int SPLASH_TIME = 1000;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);


        checkNightModeActivated();

System.out.println("Bonjour");


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run (){
                //AJOUTER LE IF SI LA PERSONNE s'est déjà connecté une fois ici
                Intent homeIntent = new Intent(LoadingScreen.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME);
    }


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
