package com.example.mytestapp.adapter;

import androidx.room.TypeConverter;

import java.util.Date;


//This class is used to store date in Long in the Database

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
