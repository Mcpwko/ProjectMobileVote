package com.example.mytestapp.db.entities;


//This class represents the Meeting Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Meeting implements Comparable{

    //The main change we did was to change the ID, which was an int, to a String


    private String mid;

    private String user_id;

    private String titleMeeting;

    private Date dayMeeting;

    private String timeMeeting;

    private String placeMeeting;

    private String descMeeting;

    private String type;

    public Meeting(){

    }

    public Meeting(String mid, String user_id, String titleMeeting, Date dayMeeting, String timeMeeting, String placeMeeting, String descMeeting, String type) {
        this.mid = mid;
        this.user_id = user_id;
        this.titleMeeting = titleMeeting;
        this.dayMeeting = dayMeeting;
        this.timeMeeting = timeMeeting;
        this.placeMeeting = placeMeeting;
        this.descMeeting = descMeeting;
        this.type = type;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }


    //Used to give the value of the keys in firebase


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("titleMeeting", titleMeeting);
        result.put("dayMeeting", dayMeeting);
        result.put("timeMeeting", timeMeeting);
        result.put("placeMeeting", placeMeeting);
        result.put("descMeeting", descMeeting);
        result.put("type", type);


        return result;
    }
}



