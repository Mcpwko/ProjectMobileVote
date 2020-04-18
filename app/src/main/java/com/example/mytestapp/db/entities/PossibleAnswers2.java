package com.example.mytestapp.db.entities;


//This class represents the User Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PossibleAnswers2 implements Comparable{

    private int paid;

    private int pollid;

    private String answer;

    public PossibleAnswers2(){

    }

    public PossibleAnswers2(int paid, int pollid, String answer) {
        this.paid = paid;
        this.pollid = pollid;
        this.answer = answer;
    }
    @Exclude
    public int getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
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

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("answer", answer);

        return result;
    }
}


