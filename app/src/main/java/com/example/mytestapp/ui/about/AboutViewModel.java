package com.example.mytestapp.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    //LIST

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This app has the purpose to be a project made during the 4th semester. The version is Android 10.");
    }

    //return LIST
    public LiveData<String> getText() {
        return mText;
    }
}