package com.johnmagdalinos.android.newsworld.utilities;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.johnmagdalinos.android.newsworld.model.Section;
import com.johnmagdalinos.android.newsworld.model.database.ArticleDao;
import com.johnmagdalinos.android.newsworld.model.database.ArticleDatabase;
import com.johnmagdalinos.android.newsworld.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gianni on 08/02/2018.
 */

public class SyncService extends IntentService {
    /** Member variables */
    private ArticleRepository mArticleRepo;


    /** Class constructor */
    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ArticleDao articleDao = Room.databaseBuilder(
                getApplicationContext(),
                ArticleDatabase.class,
                Constants.DB_NAME)
                .build()
                .articleDao();
        mArticleRepo = new ArticleRepository(articleDao, sharedPreferences, this.getApplication());

        String intentString = intent.getStringExtra(Constants.KEY_SYNC_SERVICE);
        ArrayList<Section> sections = intent.getParcelableArrayListExtra(Constants.KEY_SECTIONS);
        long prefsDateLong = intent.getLongExtra(Constants.KEY_LAST_SYNC_DATE, 0);
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
        long rows = mArticleRepo.countArticles();
        long currentDate = DataUtilities.convertCurrentDateToLong();

        // News should be deleted after 1 day
        long expirationPeriod = TimeUnit.HOURS.toMillis(8);
        boolean newsExpired = (currentDate - prefsDateLong) >= expirationPeriod;

        Log.v("testing", String.valueOf(prefsDateLong));
        if (rows > 0) {
            if (newsExpired) {
                mArticleRepo.clearArticles();
                Log.v("testing", "expired!");
                syncArticles(sections);
            } else {
                Log.v("testing", "not expired!");
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
        mArticleRepo.addArticles(sections);
    }
}
