package com.example.mytestapp.db.entities;


import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;


public class Vote implements Comparable{


    public String vid;

    public String user_id;

    public int possaid;

    public int poll_id;


    public Vote(String vid, String user_id, int possaid, int poll_id) {
        this.vid = vid;
        this.user_id = user_id;
        this.possaid = possaid;
        this.poll_id = poll_id;
    }
    @Exclude
    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getPossaid() {
        return possaid;
    }

    public void setPossaid(int possaid) {
        this.possaid = possaid;
    }

    public int getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(int poll_id) {
        this.poll_id = poll_id;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }
}


