package com.johnmagdalinos.android.newsworld.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class with various data methods
 */

public class DataUtilities {

    /** Converts the date provided by the API to a local date */
    public static String convertDate(String source) {
        String newDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = simpleDateFormat.parse(source);
            DateFormat dateFormat = DateFormat.getDateInstance();
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /** Converts the date provided by the API to time */
    public static String convertTime(String source) {
        String newTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            Date date = simpleDateFormat.parse(source);
            DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            newTime = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTime;    }
}
