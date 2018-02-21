package com.johnmagdalinos.android.newsworld.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.sectionsdb.Section;
import com.johnmagdalinos.android.newsworld.presenter.NewsPresenter;
import com.johnmagdalinos.android.newsworld.utilities.Constants;

import java.util.ArrayList;

/**
 * Main activity displaying the news. Utilizes a drawer with a list of sections provided by the
 * SplashActivity.
 */

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnClickCallback {

    /** Member variables */
    private DrawerLayout mDrawerLayout;
    private NewsPresenter mPresenter;
    private int mSelectedSection;
    private ArrayList<Section> mSections;
    private DrawerAdapter mAdapter;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the presenter
        mPresenter = new NewsPresenter();

        // Get the list of News Sections
        if (getIntent() != null) mSections = getIntent().getParcelableArrayListExtra(Constants
                .KEY_SECTION);

        // Get the last News Section from the preferences
        mSharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        mSelectedSection = mSharedPrefs.getInt(Constants.KEY_SECTION, 0);

        // Setup the navigation drawer
        mDrawerLayout = findViewById(R.id.dl_main_drawer);

        RecyclerView drawerRecyclerView = findViewById(R.id.rv_main_drawer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(linearLayoutManager);
        drawerRecyclerView.setHasFixedSize(true);

        mAdapter = new DrawerAdapter(this, this, mSections, mSelectedSection);
        drawerRecyclerView.setAdapter(mAdapter);

        loadFragment(mSelectedSection);
    }

    /** Displays the fragment with the news from the selected section */
    private void loadFragment(int position) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        String selectedSection = mSections.get(position).getSection_id();

        NewsFragment fragment = NewsFragment.newInstance(selectedSection);
        fm.beginTransaction()
                .replace(R.id.fl_main_content, fragment)
                .commit();

        fragment.setPresenter(mPresenter);

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /** Refreshes the navigation drawer's recycler view and displays the news fragment */
    @Override
    public void onItemClick(int position) {
        mSelectedSection = position;
        // Save the selected section in the SharedPreferences
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putInt(Constants.KEY_SECTION, mSelectedSection);
        editor.apply();

        // Display the correct fragment
        loadFragment(position);
    }
}
