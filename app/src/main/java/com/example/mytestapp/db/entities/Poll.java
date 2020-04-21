package com.example.mytestapp.db.entities;


//This class represents the Poll Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Poll implements Comparable{

    //The main change we did was to change the ID, which was an int, to a String

    private String pid;

    private String user_id;

    private String titlePoll;

    private String categoryPoll;

    private String descPoll;

    private Date deadlinePoll;

    private String type;

    public Poll(){

    }

    public Poll(String pid, String user_id, String titlePoll, String categoryPoll, String descPoll, Date deadlinePoll, String type) {
        this.pid = pid;
        this.user_id = user_id;
        this.titlePoll = titlePoll;
        this.categoryPoll = categoryPoll;
        this.descPoll = descPoll;
        this.deadlinePoll = deadlinePoll;
        this.type = type;
    }
    @Exclude
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    //Used to give the value of the keys in firebase

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("titlePoll", titlePoll);
        result.put("categoryPoll", categoryPoll);
        result.put("descPoll", descPoll);
        result.put("deadlinePoll", deadlinePoll);
        result.put("type", type);

        return result;
    }
}


