package com.johnmagdalinos.android.newsworld.utilities;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.johnmagdalinos.android.newsworld.R;

/**
 * Created by Gianni on 26/02/2018.
 */

public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
