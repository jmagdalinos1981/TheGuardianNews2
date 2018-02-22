package com.johnmagdalinos.android.newsworld.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.sectionsdb.Section;
import com.johnmagdalinos.android.newsworld.presenter.NewsPresenter;
import com.johnmagdalinos.android.newsworld.utilities.Constants;

import java.util.ArrayList;

/**
 * Main activity displaying the news. Utilizes a drawer with a list of sections provided by the
 * SplashActivity.
 */

public class MainActivity extends AppCompatActivity implements
        DrawerAdapter.OnClickCallback,
        NewsListFragment.ArticleCallback,
        WebViewFragment.WebViewCallback {

    /** Member variables */
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mContentLayout;
    private Toolbar mToolbar;
    private NewsPresenter mPresenter;
    private int mSelectedSection;
    private ArrayList<Section> mSections;
    private DrawerAdapter mAdapter;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private NewsListFragment mNewsListFragment;
    private WebViewFragment mWebViewFragment;
    private SharedPreferences mSharedPrefs;
    private String mCurrentFragment, mUrl;

    /** Keys for saving state */
    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    private static final String KEY_CURRENT_FRAGMENT_TITLE = "current_fragment_title";
    private static final String KEY_URL = "url";
    private static final String FRAGMENT_NEWS = "news";
    private static final String FRAGMENT_ARTICLE = "article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentLayout = findViewById(R.id.fl_main_content);
        mDrawerLayout = findViewById(R.id.dl_main_drawer);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        // Get the height of the status bar and add it tot the height of the toolbar
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mToolbar
                .getLayoutParams();
        layoutParams.height += getStatusBarHeight();

        // Set the height of the status bar as the padding for the content and toolbar
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        // Setup the Drawer Toggle
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();

        // Instantiate the presenter
        mPresenter = new NewsPresenter();

        // Get the list of News Sections
        if (getIntent() != null) mSections = getIntent().getParcelableArrayListExtra(Constants
                .KEY_SECTION);

        setupDrawer();

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mCurrentFragment = savedInstanceState.getString(KEY_CURRENT_FRAGMENT_TITLE);

            switch (mCurrentFragment) {
                case FRAGMENT_NEWS:
                    mNewsListFragment = (NewsListFragment) mFragmentManager.getFragment(savedInstanceState,
                            KEY_CURRENT_FRAGMENT);
                    break;
                case FRAGMENT_ARTICLE:
                    mWebViewFragment = (WebViewFragment) mFragmentManager.getFragment
                            (savedInstanceState, KEY_CURRENT_FRAGMENT);
                    mUrl = savedInstanceState.getString(KEY_URL);
                    break;
            }
        } else {
            loadNewsListFragment(mSelectedSection);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_FRAGMENT_TITLE, mCurrentFragment);

        if (mCurrentFragment.equals(FRAGMENT_NEWS)) {
            if (mNewsListFragment != null) {
                mFragmentManager.putFragment(outState, KEY_CURRENT_FRAGMENT, mNewsListFragment);
            }
        } else {
            outState.putString(KEY_URL, mUrl);
            mFragmentManager.putFragment(outState, KEY_CURRENT_FRAGMENT, mWebViewFragment);
        }
    }

    /** Displays the fragment with the news from the selected section */
    private void loadNewsListFragment(int position) {
        String selectedSection = mSections.get(position).getSection_id();

        mNewsListFragment = NewsListFragment.newInstance(selectedSection);
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_content, mNewsListFragment)
                .addToBackStack(null)
                .commit();

        mNewsListFragment.setPresenter(mPresenter);

        mCurrentFragment = FRAGMENT_NEWS;
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /** Displays the fragment with the WebView */
    private void loadArticle() {
        if (mWebViewFragment == null || mCurrentFragment.equals(FRAGMENT_NEWS))  {
            mWebViewFragment =
                    WebViewFragment
                            .newInstance(mUrl);
        }
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_content, mWebViewFragment)
                .addToBackStack(null)
                .commit();

        mCurrentFragment = FRAGMENT_ARTICLE;
    }
    /** Receives the clicked article's url and launches the WebView */
    @Override
    public void onArticleClicked(String url) {
        mUrl = url;
        loadArticle();
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
        loadNewsListFragment(position);
    }

    /** Sets up the Drawer */
    public void setupDrawer() {
        // Get the last News Section from the preferences
        mSharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        mSelectedSection = mSharedPrefs.getInt(Constants.KEY_SECTION, 0);

        // Setup the navigation drawer
        RecyclerView drawerRecyclerView = findViewById(R.id.rv_main_drawer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(linearLayoutManager);
        drawerRecyclerView.setHasFixedSize(true);

        mAdapter = new DrawerAdapter(this, this, mSections, mSelectedSection);
        drawerRecyclerView.setAdapter(mAdapter);
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
    public void onWebViewClosed() {
        mCurrentFragment = FRAGMENT_NEWS;
    }

    // TODO: Save NewsList position
    // TODO: Save WebView position
}
