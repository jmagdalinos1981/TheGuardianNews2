package com.johnmagdalinos.android.newsworld.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.johnmagdalinos.android.newsworld.R;

/**
 * Created by Gianni on 26/02/2018.
 */

public class PreferencesFragment extends PreferenceFragmentCompat {
    /** Member variables */
    private android.support.v14.preference.MultiSelectListPreference mMultiPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_sync);

        SharedPreferences sharedPrefs = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        mMultiPref = (android.support.v14.preference.MultiSelectListPreference) preferenceScreen
                .findPreference(getString(R.string.prefs_drawer_key));

        mMultiPref.setEntries(R.array.section_titles);
        mMultiPref.setEntryValues(R.array.section_ids);

        int prefCount = preferenceScreen.getPreferenceCount();

        // Iterate through all the preferences
        for (int i = 0; i < prefCount; i ++) {

        }

    }
}
