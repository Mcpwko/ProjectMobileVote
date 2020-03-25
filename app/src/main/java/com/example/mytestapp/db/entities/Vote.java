package com.example.mytestapp.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "vid",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Category.class,
                parentColumns = "cid",
                childColumns = "vid",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = PossibleAnswers.class,
                parentColumns = "cid",
                childColumns = "vid",
                onDelete = CASCADE
        )

})

public class Vote {

    @PrimaryKey
    private int vid;
}
