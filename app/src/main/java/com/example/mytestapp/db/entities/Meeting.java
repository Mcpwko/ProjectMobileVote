package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
//This class represents the Meeting Entity

//We use foreign keys to create links with the User Entity
@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "uid", childColumns = "user_id",
        onDelete = CASCADE),indices = {
        @Index(value = {"user_id"}
        )})

public class Meeting {
    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo (name = "user_id")
    private int user_id;

    @ColumnInfo (name = "title_meeting")
    private String titleMeeting;

    @ColumnInfo (name = "day_meeting")
    private Date dayMeeting;

    @ColumnInfo (name = "time_meeting")
    private String timeMeeting;

    @ColumnInfo (name = "place_meeting")
    private String placeMeeting;

    @ColumnInfo (name = "desc_meeting")
    private String descMeeting;

    @ColumnInfo (name = "statusOpen")
    private boolean statusOpen;

    public int getMid() {
        return mid;
    }

    public  void setMid(int mid) {
        this.mid = mid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isStatusOpen() {
        return statusOpen;
    }

    public void setStatusOpen(boolean statusOpen) {
        this.statusOpen = statusOpen;
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
}
