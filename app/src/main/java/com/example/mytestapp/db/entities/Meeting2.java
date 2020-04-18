package com.example.mytestapp.db.entities;


//This class represents the User Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Meeting2 implements Comparable{

    private String mid;

    private String user_id;

    private String titleMeeting;

    private Date dayMeeting;

    private String timeMeeting;

    private String placeMeeting;

    private String descMeeting;

    private boolean statusOpen;

    public Meeting2(){

    }

    public Meeting2(String mid, String user_id, String titleMeeting, Date dayMeeting, String timeMeeting, String placeMeeting, String descMeeting, boolean statusOpen) {
        this.mid = mid;
        this.user_id = user_id;
        this.titleMeeting = titleMeeting;
        this.dayMeeting = dayMeeting;
        this.timeMeeting = timeMeeting;
        this.placeMeeting = placeMeeting;
        this.descMeeting = descMeeting;
        this.statusOpen = statusOpen;
    }
    @Exclude
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitleMeeting() {
        return titleMeeting;
    }

    public void setTitleMeeting(String titleMeeting) {
        this.titleMeeting = titleMeeting;
    }

    public Date getDayMeeting() {
        return dayMeeting;
    }

    public void setDayMeeting(Date dayMeeting) {
        this.dayMeeting = dayMeeting;
    }

    public String getTimeMeeting() {
        return timeMeeting;
    }

    public void setTimeMeeting(String timeMeeting) {
        this.timeMeeting = timeMeeting;
    }

    public String getPlaceMeeting() {
        return placeMeeting;
    }

    public void setPlaceMeeting(String placeMeeting) {
        this.placeMeeting = placeMeeting;
    }

    public String getDescMeeting() {
        return descMeeting;
    }

    public void setDescMeeting(String descMeeting) {
        this.descMeeting = descMeeting;
    }

    public boolean isStatusOpen() {
        return statusOpen;
    }

    public void setStatusOpen(boolean statusOpen) {
        this.statusOpen = statusOpen;
    }



    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("titleMeeting", titleMeeting);
        result.put("dayMeeting", dayMeeting);
        result.put("timeMeeting", timeMeeting);
        result.put("placeMeeting", placeMeeting);
        result.put("descMeeting", descMeeting);
        result.put("statusOpen", statusOpen);


        return result;
    }
}



