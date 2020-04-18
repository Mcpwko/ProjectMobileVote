package com.example.mytestapp.db.entities;


//This class represents the User Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Poll2 implements Comparable{


    private String pid;

    private int user_id;

    private String titlePoll;

    private String categoryPoll;

    private String descPoll;

    private Date deadlinePoll;

    private boolean statusOpen;

    public Poll2(){

    }

    public Poll2(String pid, int user_id, String titlePoll, String categoryPoll, String descPoll, Date deadlinePoll, boolean statusOpen) {
        this.pid = pid;
        this.user_id = user_id;
        this.titlePoll = titlePoll;
        this.categoryPoll = categoryPoll;
        this.descPoll = descPoll;
        this.deadlinePoll = deadlinePoll;
        this.statusOpen = statusOpen;
    }
    @Exclude
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitlePoll() {
        return titlePoll;
    }

    public void setTitlePoll(String titlePoll) {
        this.titlePoll = titlePoll;
    }

    public String getCategoryPoll() {
        return categoryPoll;
    }

    public void setCategoryPoll(String categoryPoll) {
        this.categoryPoll = categoryPoll;
    }

    public String getDescPoll() {
        return descPoll;
    }

    public void setDescPoll(String descPoll) {
        this.descPoll = descPoll;
    }

    public Date getDeadlinePoll() {
        return deadlinePoll;
    }

    public void setDeadlinePoll(Date deadlinePoll) {
        this.deadlinePoll = deadlinePoll;
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
        result.put("titlePoll", titlePoll);
        result.put("categoryPoll", categoryPoll);
        result.put("descPoll", descPoll);
        result.put("deadlinePoll", deadlinePoll);
        result.put("statusOpen", statusOpen);

        return result;
    }
}


