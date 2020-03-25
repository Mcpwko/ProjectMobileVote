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
                childColumns = "tbl_uid",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Category.class,
                parentColumns = "cid",
                childColumns = "tbl_cid",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = PossibleAnswers.class,
                parentColumns = "paid",
                childColumns = "tbl_paid",
                onDelete = CASCADE
        )

})

public class Vote {


    @ColumnInfo(name="tbl_uid")
    public int uid;

    @ColumnInfo(name = "tbl_cid")
    public int cid;

    @ColumnInfo(name = "tbl_paid")
    public int paid;
}
