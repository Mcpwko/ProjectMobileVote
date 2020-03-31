package com.example.mytestapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


public class HomeViewModel extends AndroidViewModel {

    private Application application;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;




    }



}