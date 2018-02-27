package com.johnmagdalinos.android.newsworld.utilities;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.sectionsdb.Section;
import com.johnmagdalinos.android.newsworld.model.sectionsdb.SectionDatabase;

/**
 * Created by Gianni on 08/02/2018.
 */

public class SectionsService extends IntentService {
    /** Member variables */
    SectionDatabase mDatabase;

    /** Class constructor */
    public SectionsService() {
        super("SectionsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Check if this is the first time the app is running
        SharedPreferences sharedPrefs = getSharedPreferences(Constants.PREFS_FILENAME, Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPrefs.getBoolean(Constants.KEY_FIRST_RUN, true);

        // Get an instance of the Sections Database
        mDatabase = Room.databaseBuilder(getApplicationContext(), SectionDatabase.class,
                "sections").build();
        if (isFirstRun) initialSetup();

        // Get only the selected sections

        Intent resultIntent = new Intent(Constants.SECTIONS_BROADCAST_ACTION);
//        resultIntent.putParcelableArrayListExtra(Constants.KEY_SECTIONS_BROADCAST_EXTRAS, sections);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(resultIntent);
    }

    /** Inserts all the sections in the database and sets the selected ones by default */
    private void initialSetup() {
        // Get the section ids and titles from the resources
        String[] sectionTitles = getApplicationContext().getResources().getStringArray(R.array
                .section_titles);
        String[] sectionIds = getApplicationContext().getResources().getStringArray(R.array
                .section_ids);
        Section[] sections = new Section[sectionIds.length];

        boolean isSelected;
        for (int i = 0; i < sections.length; i++) {
            // Show only 5 sections at first run
            if (sectionIds[i].equals("allnews") ||
                    sectionIds[i].equals("business") ||
                    sectionIds[i].equals("lifeandstyle") ||
                    sectionIds[i].equals("technology") ||
                    sectionIds[i].equals("world")) {
                isSelected = true;
            } else {
                isSelected = false;
            }
            Section section = new Section(sectionIds[i], sectionTitles[i]);
            sections[i] = section;
        }

        // Insert the sections in the database
        mDatabase.sectionDao().insertSections(sections);
    }
}
