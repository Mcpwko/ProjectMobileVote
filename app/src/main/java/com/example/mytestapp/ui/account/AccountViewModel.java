package com.example.mytestapp.ui.account;

import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytestapp.R;
import com.google.gson.Gson;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AccountViewModel() {


        mText = new MutableLiveData<>();
        mText.setValue("This is account page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}