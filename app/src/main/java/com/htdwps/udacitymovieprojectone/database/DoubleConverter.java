package com.htdwps.udacitymovieprojectone.database;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by HTDWPS on 8/14/18.
 */
public class DoubleConverter {

    @TypeConverter
    public static Double toDouble(String average) {
        if (average == null) {
            return null;
        } else {
            return Double.parseDouble(average);
        }
    }

    @TypeConverter
    public static String toString(Double value) {
        if (value == null) {
            return null;
        } else {
            return String.valueOf(value);
        }
    }

}
