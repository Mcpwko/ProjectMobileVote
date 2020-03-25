package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Meeting.class,
                parentColumns = "mid",
                childColumns = "aid",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "aid",
                onDelete = CASCADE
        )
})

public class Attendance {

    @PrimaryKey
    private int aid;

    @ColumnInfo (name = "answer_attendance")
    private boolean answerAttendance;

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
}
