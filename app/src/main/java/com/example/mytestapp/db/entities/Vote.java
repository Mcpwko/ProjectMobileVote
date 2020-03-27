package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "user_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = PossibleAnswers.class,
                parentColumns = "paid",
                childColumns = "possaid",
                onDelete = CASCADE
        )},indices = {
        @Index(value ={"user_id"}),
        @Index(value ={"possaid"})
})

public class Vote {

    @PrimaryKey(autoGenerate = true)
    public int vid;

    @ColumnInfo(name="user_id")
    public int user_id;

    @ColumnInfo(name = "possaid")
    public int possaid;


    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPossaid() {
        return possaid;
    }

    public void setPossaid(int possaid) {
        this.possaid = possaid;
    }
}
