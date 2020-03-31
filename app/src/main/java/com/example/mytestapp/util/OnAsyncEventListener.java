package com.example.mytestapp.util;

//This class is used to give the method OnAsyncEventListener. A callback for events passing through
// the AsyncEventQueue to which this listener is attached.
public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}