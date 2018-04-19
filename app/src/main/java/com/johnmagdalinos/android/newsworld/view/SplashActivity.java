package com.johnmagdalinos.android.newsworld.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.Section;
import com.johnmagdalinos.android.newsworld.utilities.Constants;
import com.johnmagdalinos.android.newsworld.utilities.DataUtilities;
import com.johnmagdalinos.android.newsworld.utilities.SyncService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Launcher Activity. Displays a splash screen while loading the News Sections.
 * It then launches the main activity to which it passes the loaded Sections.
 */

public class SplashActivity extends AppCompatActivity {
    /** Member variables */
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Get the selected sections and the last date of sync
        ArrayList<Section> sections = getSections();
        long currentDate = mSharedPreferences.getLong(Constants.KEY_LAST_SYNC_DATE, 0);

        // Start the service to sync the data
        Intent syncIntent = new Intent(SplashActivity.this, SyncService.class);
        syncIntent.putExtra(Constants.KEY_SYNC_SERVICE, Constants.GET_ARTICLES);
        syncIntent.putExtra(Constants.KEY_LAST_SYNC_DATE, currentDate);
        syncIntent.putParcelableArrayListExtra(Constants.KEY_SECTIONS, sections);
        startService(syncIntent);

        // Start the Main Activity
        Intent activityIntent = new Intent(SplashActivity.this, MainActivity.class);
        activityIntent.putParcelableArrayListExtra(Constants.KEY_SECTIONS, sections);
        startActivity(activityIntent);
        finish();
    }

    /** Load the selected News Sections from the preferences */
    private ArrayList<Section> getSections() {
        ArrayList<Section> sections;
        ArrayList<String> selectedSections;

        // Get the user-selected sections
        Set<String> set = mSharedPreferences.getStringSet(getString(R.string.prefs_drawer_key), null);

        if (set != null) {
            // Sections have been changed
            selectedSections = new ArrayList<>(set);
        } else {
            // Get the default sections
            String[] defaultSections = getResources().getStringArray(R.array.section_default);

            selectedSections = new ArrayList<>(Arrays.asList(defaultSections));
        }
        sections = DataUtilities.getSelectedSections(this, selectedSections);

        return sections;
    }
}
