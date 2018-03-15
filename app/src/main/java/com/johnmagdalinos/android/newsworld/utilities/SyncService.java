package com.johnmagdalinos.android.newsworld.utilities;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.johnmagdalinos.android.newsworld.model.Section;
import com.johnmagdalinos.android.newsworld.model.articlesdb.ArticleDatabase;
import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gianni on 08/02/2018.
 */

public class SyncService extends IntentService implements NetworkUtils.RetrofitCallback {
    /** Member variables */
    private ArticleDatabase mDb;


    /** Class constructor */
    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mDb = Room.databaseBuilder(getApplicationContext(), ArticleDatabase.class, "articles")
                .build();

        String intentString = intent.getStringExtra(Constants.KEY_SYNC_SERVICE);
        ArrayList<Section> sections = intent.getParcelableArrayListExtra(Constants.KEY_SECTIONS);
        long prefsDateLong = intent.getLongExtra(Constants.KEY_CURRENT_DATE, 0);
        switch (intentString) {
            case Constants.GET_ARTICLES:
                getArticles(sections, prefsDateLong);
                break;
            case Constants.SEARCH_ARTICLES:
                searchArticles(sections);
                break;
        }
    }

    /** Retrieves the articles from the API */
    private void getArticles(ArrayList<Section> sections, long prefsDateLong) {
        long rows = mDb.articleDao().countRows();
        long currentDate = System.currentTimeMillis();

        // News should be deleted after 1 day
        long expirationPeriod = TimeUnit.DAYS.toMillis(1);
        boolean newsExpired = (currentDate - prefsDateLong) >= expirationPeriod;

        if (rows > 0) {
            if (newsExpired) {
                mDb.articleDao().deleteAllArticles();
                syncArticles(sections);
            }
        } else {
            syncArticles(sections);
        }


    }

    /** Searches the API for the requested articles */
    private void searchArticles(ArrayList<Section> sections) {

    }

    /** Syncs the articles */
    private void syncArticles(ArrayList<Section> sections) {
        NetworkUtils networkUtils = new NetworkUtils();
        networkUtils.start(this, sections);
    }

    @Override
    public void onRetrofitCompleted(ArrayList<NewsArticle> articles) {
        NewsArticle[] newsArticles = articles.toArray(new NewsArticle[articles.size()]);
        mDb.articleDao().insertArticles(newsArticles);
    }
}
