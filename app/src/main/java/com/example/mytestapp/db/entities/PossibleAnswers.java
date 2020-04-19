package com.example.mytestapp.db.entities;


//This class represents the User Entity
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PossibleAnswers implements Comparable{

    private String paid;

    private String pollid;

    private String answer;

    public PossibleAnswers(){

    }

    public PossibleAnswers(String paid, String pollid, String answer) {
        this.paid = paid;
        this.pollid = pollid;
        this.answer = answer;
    }
    @Exclude
    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPollid() {
        return pollid;
    }

    public void setPollid(String pollid) {
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


