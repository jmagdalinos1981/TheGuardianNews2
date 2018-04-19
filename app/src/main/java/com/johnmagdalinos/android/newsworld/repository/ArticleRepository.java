package com.johnmagdalinos.android.newsworld.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.johnmagdalinos.android.newsworld.model.JSONObject;
import com.johnmagdalinos.android.newsworld.model.Section;
import com.johnmagdalinos.android.newsworld.model.database.Article;
import com.johnmagdalinos.android.newsworld.model.database.ArticleDao;
import com.johnmagdalinos.android.newsworld.utilities.ApiKeys;
import com.johnmagdalinos.android.newsworld.utilities.Constants;
import com.johnmagdalinos.android.newsworld.utilities.DataUtilities;
import com.johnmagdalinos.android.newsworld.utilities.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gianni on 16/03/2018.
 */

@Singleton
public class ArticleRepository {
    private static WebService mWebService;
    private static ArticleDao mArticleDao;
    private static SharedPreferences mSharedPrefs;
    private static Application mApplication;

    /** Base url of the api */
    private static final String BASE_URL = "https://content.guardianapis.com";

    @Inject
    public ArticleRepository(ArticleDao articleDao, SharedPreferences sharedPreferences,
                             Application application) {
        mArticleDao = articleDao;
        mSharedPrefs = sharedPreferences;
        mApplication = application;
    }

    /** Used to load articles from a section */
    public LiveData<List<Article>> loadArticles(String sectionId) {
        return mArticleDao.loadSectionArticles(sectionId);
    }

    /** Used to load all articles */
    public LiveData<List<Article>> loadAllArticles() {
        return mArticleDao.loadAllArticles();
    }

    /** Used to count the number of articles */
    public long countArticles() {
        return mArticleDao.countRows();
    }

    /** Used to delete all articles */
    public void clearArticles() {
        mArticleDao.deleteAllArticles();
    }

    /** Used to store the articles in the database */
    public void addArticles(List<Section> sections) {
        new AddArticlesToDbTask().execute(sections);
    }

    private static class AddArticlesToDbTask extends AsyncTask<List<Section>, Void, Void> {

        @Override
        protected Void doInBackground(List<Section>... sectionList) {
            List<Section> sections = sectionList[0];

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mWebService = retrofit.create(WebService.class);

            Call<JSONObject> call;

            for (Section section : sections) {
                String sectionId = section.getSection_id();
                // Check if the user selected "All news"
                if (sectionId.equals(Constants.KEY_ALL_NEWS)) {
                    call = mWebService.loadArticles(null, ApiKeys.API_KEY);
                } else {
                    call = mWebService.loadArticles(sectionId, ApiKeys.API_KEY);
                }

                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        JSONObject model = (JSONObject) response.body();
                        JSONObject.Response jsonResponse = model.getResponse();
                        ArrayList<Article> articles = jsonResponse.getResults();

                        // Convert the ArrayList to an Array and insert values into the database
                        Article[] newsArticles = articles.toArray(new Article[articles.size()]);

                        mArticleDao.insertArticles(newsArticles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Save the date and time of insertion
            SharedPreferences.Editor editor = mSharedPrefs.edit();
            editor.putLong(Constants.KEY_LAST_SYNC_DATE, DataUtilities.convertCurrentDateToLong());
            editor.commit();

            // Notify the broadcast receiver that the sync is complete
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_SYNC_COMPLETED);
            mApplication.sendBroadcast(intent);
        return null;
        }
    }
}
