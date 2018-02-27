package com.johnmagdalinos.android.newsworld.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.utilities.Constants;

/**
 * Activity used to display a webpage in a WebView
 */

public class WebViewActivity extends AppCompatActivity {
    /** Member variables */
    private WebView mWebView;
    private String mUrl;
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        setupToolbar();

        mUrl = getIntent().getStringExtra(Constants.KEY_URL);

        setupWebView();
    }

    /** Sets up the toolbar */
    public void setupToolbar() {
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        // Get the height of the status bar and add it tot the height of the toolbar
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mToolbar
                .getLayoutParams();
        layoutParams.height += getStatusBarHeight();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the height of the status bar as the padding for the content and toolbar
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    /** Setup WebView */
    private void setupWebView() {
        mWebView = findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
