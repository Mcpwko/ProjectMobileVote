package com.example.mytestapp.db.entities;


import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class Vote implements Comparable{

    public String vid;

    public String user_id;

    public String possaid;

    public String poll_id;

    public Vote(){

    }

    public Vote(String vid,String user_id, String possaid, String poll_id) {
        this.user_id = user_id;
        this.possaid = possaid;
        this.poll_id = poll_id;
        this.vid = vid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPossaid() {
        return possaid;
    }

    public void setPossaid(String possaid) {
        this.possaid = possaid;
    }

    public String getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(String poll_id) {
        this.poll_id = poll_id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", user_id);
        result.put("possaid",possaid);

        return result;
    }
}


