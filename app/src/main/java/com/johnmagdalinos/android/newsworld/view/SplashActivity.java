package com.johnmagdalinos.android.newsworld.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.sectionsdb.Section;
import com.johnmagdalinos.android.newsworld.utilities.Constants;
import com.johnmagdalinos.android.newsworld.utilities.DataUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Launcher Activity. Displays a splash screen while loading the News Sections.
 * It then launches the main activity to which it passes the loaded Sections.
 */

public class SplashActivity extends AppCompatActivity {
    /** Member variables */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Section> sections = getSections();

        Intent activityIntent = new Intent(SplashActivity.this, MainActivity.class);
        activityIntent.putParcelableArrayListExtra(Constants.KEY_SECTION, sections);
        startActivity(activityIntent);
        finish();
    }

    /** Load the selected News Sections from the preferences */
    private ArrayList<Section> getSections() {
        ArrayList<Section> sections;
        ArrayList<String> selectedSections;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isFirstRun = sharedPreferences.getBoolean(Constants.KEY_FIRST_RUN, true);

        if (isFirstRun) {
            // Get the default sections
            String[] defaultSections = getResources().getStringArray(R.array.section_default);

            selectedSections = new ArrayList<>(Arrays.asList(defaultSections));

            // Save the sections in the preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.KEY_FIRST_RUN, false);
            editor.apply();

        } else {
            // Get the user-selected sections
            Set<String> set = sharedPreferences.getStringSet(getString(R.string.prefs_drawer_key), null);
            selectedSections = new ArrayList<>(set);
        }

        sections = DataUtilities.getSelectedSections(this, selectedSections);


        return sections;
    }
}
