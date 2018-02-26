package com.johnmagdalinos.android.newsworld.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.johnmagdalinos.android.newsworld.model.NewsArticle;
import com.johnmagdalinos.android.newsworld.model.sectionsdb.Section;
import com.johnmagdalinos.android.newsworld.presenter.MvPContract;
import com.johnmagdalinos.android.newsworld.presenter.NewsPresenter;
import com.johnmagdalinos.android.newsworld.utilities.Constants;
import com.johnmagdalinos.android.newsworld.utilities.SettingsActivity;

import java.util.ArrayList;

/**
 * Main activity displaying the news. Utilizes a drawer with a list of sections provided by the
 * SplashActivity.
 */

public class MainActivity extends AppCompatActivity implements
        DrawerAdapter.OnClickCallback,
        MvPContract.BaseView,
        NewsAdapter.NewsAdapterCallback {

    /** Member variables */
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NewsPresenter mPresenter;
    private int mSelectedSection;
    private ArrayList<Section> mSections;
    private RecyclerView mRecyclerView;
    private DrawerAdapter mDrawerAdapter;
    private NewsAdapter mNewsAdapter;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the list of News Sections
        if (getIntent() != null) mSections = getIntent().getParcelableArrayListExtra(Constants
                .KEY_SECTION);

        setupToolbar();

        setupDrawer();

        // Instantiate the presenter
        mPresenter = new NewsPresenter();

        displayNewsList(mSelectedSection);

    }

    /** Sets up the toolbar */
    public void setupToolbar() {
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();

        // Get the last News Section from the preferences
        mSharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        mSelectedSection = mSharedPrefs.getInt(Constants.KEY_SECTION, 0);

        // Setup the navigation drawer
        RecyclerView drawerRecyclerView = findViewById(R.id.rv_main_drawer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(linearLayoutManager);
        drawerRecyclerView.setHasFixedSize(true);

        mDrawerAdapter = new DrawerAdapter(this, this, mSections, mSelectedSection);
        drawerRecyclerView.setAdapter(mDrawerAdapter);
    }

    /** Displays the news from the selected section */
    private void displayNewsList(int position) {
        String selectedSection = mSections.get(position).getSection_id();

        mRecyclerView = findViewById(R.id.rv_news_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(drawable);
        mRecyclerView.addItemDecoration(itemDecoration);
        mNewsAdapter = new NewsAdapter(this,null);
        mRecyclerView.setAdapter(mNewsAdapter);

        mPresenter.inputToPresenter(this, selectedSection);

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /** Displays the fragment with the WebView */
    private void loadArticle() {

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

    /** Refreshes the news list */
    @Override
    public void showNews(ArrayList<NewsArticle> articles) {
        mNewsAdapter.swapList(articles);
    }

    /** Displays an error message */
    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void NewsClicked(String url) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra(Constants.KEY_URL, url);
        startActivity(intent);
    }

    // TODO: Save NewsList position
    // TODO: Save WebView position
}
