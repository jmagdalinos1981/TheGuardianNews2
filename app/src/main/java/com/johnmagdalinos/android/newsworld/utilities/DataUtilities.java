package com.johnmagdalinos.android.newsworld.utilities;

import android.content.Context;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.sectionsdb.Section;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    /** Returns an ArrayList selected Sections by comparing the list of full ones with the
     * provided ArrayList of Strings
     */
    public static ArrayList<Section> getSelectedSections(Context context, ArrayList<String>
            source) {
        // Compare the full list of sections with the list of unselected ones and keep the
        // selected ones
        ArrayList<Section> allSections = getAllSections(context);
        ArrayList<Section> newSections = new ArrayList<>();

        for (Section section : allSections) {
            if ((source.contains(section.getSection_id()))) {
                newSections.add(section);
            }
        }

        // We want at least one section, so use the first one ("All news")
        if (newSections.size() == 0) {
            newSections.add(new Section(context.getString(R.string.section_id_all_news), context
                    .getString(R.string.menu_all_news)));
        }

        return newSections;
    }

    /** Creates an ArrayList with all the Sections */
    private static ArrayList<Section> getAllSections(Context context) {
        String[] sections = context.getResources().getStringArray(R.array.section_titles);
        String[] sectionsIds = context.getResources().getStringArray(R.array.section_ids);
        ArrayList<Section> allSections = new ArrayList<>();
        for (int i = 0; i < sections.length; i ++) {
            allSections.add(new Section(sectionsIds[i], sections[i]));
        }

        return allSections;
    }
}
