package com.example.mytestapp.util;

public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}