package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
//This class represents the PossibleAnswers Entity

//We use foreign keys to create links with the Poll Entity
@Entity(foreignKeys = @ForeignKey(entity = Poll.class,
        parentColumns = "pid", childColumns = "pollid",
        onDelete = CASCADE),
        indices = {
        @Index(value = {"pollid"}
        )})

public class PossibleAnswers {

    @PrimaryKey(autoGenerate = true)
    private int paid;

    @ColumnInfo (name = "pollid")
    private int pollid;

    @ColumnInfo (name = "answer")
    private String answer;



    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getPollid() {
        return pollid;
    }

    public void setPollid(int pollid) {
        this.pollid = pollid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
