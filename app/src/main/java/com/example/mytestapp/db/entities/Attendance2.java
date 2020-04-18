package com.example.mytestapp.db.entities;


//This class represents the User Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Attendance2 implements Comparable{

    private int aid;

    private int user_id;

    private int meeting_id;

    private boolean answerAttendance;

    public Attendance2(){

    }

    public Attendance2(int aid, int user_id, int meeting_id, boolean answerAttendance) {
        this.aid = aid;
        this.user_id = user_id;
        this.meeting_id = meeting_id;
        this.answerAttendance = answerAttendance;
    }

    @Exclude
    public int getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
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



