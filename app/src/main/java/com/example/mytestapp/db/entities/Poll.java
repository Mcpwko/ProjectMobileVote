package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "pid",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Category.class,
                parentColumns = "cid",
                childColumns = "pid",
                onDelete = CASCADE
        )
})


public class Poll {
    @PrimaryKey
    private int pid;

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
