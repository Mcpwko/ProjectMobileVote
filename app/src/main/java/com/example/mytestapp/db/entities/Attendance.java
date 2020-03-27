package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Meeting.class,
                parentColumns = "mid",
                childColumns = "meeting_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "user_id",
                onDelete = CASCADE
        )
},indices = {
        @Index(value = {"user_id"}),
        @Index(value ={"meeting_id"})
})

public class Attendance {

    @PrimaryKey(autoGenerate = true)
    private int aid;

    @ColumnInfo (name = "user_id")
    private int user_id;

    @ColumnInfo (name = "meeting_id")
    private int meeting_id;

    @ColumnInfo (name = "answer_attendance")
    private boolean answerAttendance;

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

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public boolean isAnswerAttendance(int uid, int idmeeting) {
        return answerAttendance;
    }

    public void setAnswerAttendance(boolean answerAttendance) {
        this.answerAttendance = answerAttendance;
    }

    public boolean isAnswerAttendance() {
        return answerAttendance;
    }
}
