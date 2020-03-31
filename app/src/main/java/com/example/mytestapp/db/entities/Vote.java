package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

//This class represents the Vote Entity. It is used as an Association Class.

//We use foreign keys to create links with the User Entity, the Poll Entity and the PossibleAnswers Entity

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "user_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Poll.class,
                parentColumns = "pid",
                childColumns = "poll_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = PossibleAnswers.class,
                parentColumns = "paid",
                childColumns = "possaid",
                onDelete = CASCADE
        )},indices = {
        @Index(value ={"user_id"}),
        @Index(value ={"possaid"}),
        @Index(value ={"poll_id"})
})

public class Vote {

    @PrimaryKey(autoGenerate = true)
    public int vid;

    @ColumnInfo(name="user_id")
    public int user_id;

    @ColumnInfo(name = "possaid")
    public int possaid;

    @ColumnInfo(name = "poll_id")
    public int poll_id;


    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(int poll_id) {
        this.poll_id = poll_id;
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
