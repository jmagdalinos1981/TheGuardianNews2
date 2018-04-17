package com.johnmagdalinos.android.newsworld.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.Section;
import com.johnmagdalinos.android.newsworld.settings.SettingsActivity;
import com.johnmagdalinos.android.newsworld.utilities.Constants;
import com.johnmagdalinos.android.newsworld.utilities.DataUtilities;
import com.johnmagdalinos.android.newsworld.view.adapter.DrawerAdapter;
import com.johnmagdalinos.android.newsworld.view.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.Set;

/**
 * Main activity displaying the news. Utilizes a drawer with a list of sections provided by the
 * SplashActivity.
 */

public class MainActivity extends AppCompatActivity implements
        DrawerAdapter.OnClickCallback,
        NewsFragment.NewsCallback,
        SharedPreferences.OnSharedPreferenceChangeListener {

    /** Member variables */
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private int mSelectedSection;
    private ArrayList<Section> mCurrentSections;
    private RecyclerView mRecyclerView, mDrawerRecyclerView;
    private DrawerAdapter mDrawerAdapter;
    private NewsAdapter mNewsAdapter;
    private SharedPreferences mSharedPrefs;

    /** Flag stating that a preference has changed */
    private boolean mPreferenceChanged = false;

    /** Keys for saving instance state */
    private final String KEY_CURRENT_DRAWER_LIST = "drawer_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the list of News Sections
        if (savedInstanceState == null) {
            if (getIntent() != null && !mPreferenceChanged) mCurrentSections = getIntent().getParcelableArrayListExtra
                    (Constants.KEY_SECTIONS);
        } else {
            mCurrentSections = savedInstanceState.getParcelableArrayList(KEY_CURRENT_DRAWER_LIST);
        }

        setupToolbar();
        setupDrawer();
        displayNewsList(mSelectedSection);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_CURRENT_DRAWER_LIST, mCurrentSections);
    }

    /** Register the OnSharedPreferenceChangeListener */
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    /** Unregister the OnSharedPreferenceChangeListener */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /** Sets up the toolbar */
    public void setupToolbar() {
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Get the height of the status bar and add it tot the height of the toolbar
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mToolbar
                .getLayoutParams();
        layoutParams.height += getStatusBarHeight();

        // Set the height of the status bar as the padding for the content and toolbar
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    /** Sets up the Drawer */
    public void setupDrawer() {
        mDrawerLayout = findViewById(R.id.dl_main_drawer);

        // Setup the Drawer Toggle
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Get the last News Section from the preferences
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSelectedSection = mSharedPrefs.getInt(Constants.KEY_SECTIONS, 0);

        // Setup the navigation drawer
        mDrawerRecyclerView = findViewById(R.id.rv_main_drawer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mDrawerRecyclerView.setLayoutManager(linearLayoutManager);
        mDrawerRecyclerView.setHasFixedSize(true);

        mDrawerAdapter = new DrawerAdapter(this, this, mCurrentSections, mSelectedSection);
        mDrawerRecyclerView.setAdapter(mDrawerAdapter);
    }

    /** Displays the news from the selected section */
    private void displayNewsList(int position) {
        if (position > mCurrentSections.size() - 1) position = 0;
        String selectedSection = mCurrentSections.get(position).getSection_id();

        FragmentManager fragmentManager = getSupportFragmentManager();
        NewsFragment fragment = NewsFragment.newInstance(selectedSection);
        fragmentManager.beginTransaction()
                .replace(R.id.fl_fragment, fragment)
                .commit();

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /** Refreshes the navigation drawer's recycler view and displays the news fragment */
    @Override
    public void onItemClick(int position) {
        mSelectedSection = position;
        // Save the selected section in the SharedPreferences
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putInt(Constants.KEY_SECTIONS, mSelectedSection);
        editor.apply();

        // Display the correct fragment
        displayNewsList(position);
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

    /** Launches the WebViewActivity to display the selected article */
    @Override
    public void onNewsClicked(String url) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra(Constants.KEY_URL, url);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mPreferenceChanged = true;

        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putBoolean(Constants.KEY_PREFS_CHANGED, true);
        editor.apply();

        if (key.equals(getString(R.string.prefs_drawer_key))) {
            // Get the unselected values for the sections
            Set<String> set = mSharedPrefs.getStringSet(getString(R.string.prefs_drawer_key), null);
            ArrayList<String> selectedSections = new ArrayList<>(set);

            mCurrentSections = DataUtilities.getSelectedSections(this, selectedSections);

            // Refresh the drawer
            mDrawerAdapter.swapList(mCurrentSections);
            mDrawerRecyclerView.setAdapter(mDrawerAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: Save NewsList position
    // TODO: Save WebView position
}
