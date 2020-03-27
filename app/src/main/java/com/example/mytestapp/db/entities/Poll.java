package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "uid", childColumns = "user_id",
        onDelete = CASCADE),indices = {
        @Index(value = {"user_id"}
        )})


public class Poll {
    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo (name = "user_id")
    private int user_id;

    @ColumnInfo (name = "title_poll")
    private String titlePoll;

    @ColumnInfo (name = "category_poll")
    private String categoryPoll;

    @ColumnInfo (name = "desc_poll")
    private String descPoll;

    @ColumnInfo (name = "deadline_poll")
    private String deadlinePoll;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
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

    public String getDeadlinePoll() {
        return deadlinePoll;
    }

    public void setDeadlinePoll(String deadlinePoll) {
        this.deadlinePoll = deadlinePoll;
    }
}
