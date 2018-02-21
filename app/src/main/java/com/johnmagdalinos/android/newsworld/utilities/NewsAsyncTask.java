package com.johnmagdalinos.android.newsworld.utilities;

import android.os.AsyncTask;

import com.johnmagdalinos.android.newsworld.model.NewsArticle;

import java.util.ArrayList;

/**
 * AsyncTask used to start Retrofit to download articles from the API
 */

public class NewsAsyncTask extends AsyncTask<String, Void, ArrayList<NewsArticle>> implements
    NetworkUtils.RetrofitCallback {

    /** Member variables */
    private onTaskCompletedCallback mCallback;

    public interface onTaskCompletedCallback {
        void result(ArrayList<NewsArticle> articles);
    }

    public void setAsyncTaskCallback(onTaskCompletedCallback callback, String section) {
        this.mCallback = callback;

        execute(section);
    }

    @Override
    protected ArrayList<NewsArticle> doInBackground(String... strings) {
        NetworkUtils networkUtils = new NetworkUtils();
        networkUtils.start(this, strings[0]);

        return null;
    }

    @Override
    public void onRetrofitCompleted(ArrayList<NewsArticle> articles) {
        mCallback.result(articles);
    }
}
