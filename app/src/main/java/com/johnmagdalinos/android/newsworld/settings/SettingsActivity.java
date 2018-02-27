package com.johnmagdalinos.android.newsworld.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.johnmagdalinos.android.newsworld.R;

/**
 * Created by Gianni on 26/02/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Get the height of the status bar and add it tot the height of the toolbar
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar
                .getLayoutParams();
        layoutParams.height += getStatusBarHeight();

        // Set the height of the status bar as the padding for the content and toolbar
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    /** Calculates the StatusBar height to use it as a padding for the toolbar */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen","android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
