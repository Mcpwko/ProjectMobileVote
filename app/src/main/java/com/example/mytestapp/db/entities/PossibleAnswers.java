package com.example.mytestapp.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Poll.class,
        parentColumns = "pid", childColumns = "paid",
        onDelete = CASCADE))

public class PossibleAnswers {

    @PrimaryKey
    private int paid;

    @ColumnInfo (name = "answer")
    private String answer;


    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
