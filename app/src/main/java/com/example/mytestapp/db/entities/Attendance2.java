package com.example.mytestapp.db.entities;


//This class represents the User Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Attendance2 implements Comparable{

    private String aid;

    private String user_id;

    private String meeting_id;

    private boolean answerAttendance;

    public Attendance2(){

    }

    public Attendance2(String aid, String user_id, String meeting_id, boolean answerAttendance) {
        this.aid = aid;
        this.user_id = user_id;
        this.meeting_id = meeting_id;
        this.answerAttendance = answerAttendance;
    }

    @Exclude
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public boolean isAnswerAttendance() {
        return answerAttendance;
    }

    public void setAnswerAttendance(boolean answerAttendance) {
        this.answerAttendance = answerAttendance;
    }



    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("answerAttendance", answerAttendance);

        return result;
    }
}



