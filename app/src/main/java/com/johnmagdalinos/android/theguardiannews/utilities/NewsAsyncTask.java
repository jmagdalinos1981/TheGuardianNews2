package com.johnmagdalinos.android.theguardiannews.utilities;

import android.os.AsyncTask;

import com.johnmagdalinos.android.theguardiannews.model.NewsArticle;

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

    public void setAsyncTaskCallback(String section, onTaskCompletedCallback callback) {
        this.mCallback = callback;

        execute(section);
    }

    @Override
    protected ArrayList<NewsArticle> doInBackground(String... strings) {
        NetworkUtils networkUtils = new NetworkUtils();
        networkUtils.start(this);

        return null;
    }

    @Override
    public void onRetrofitCompleted(ArrayList<NewsArticle> articles) {
        mCallback.result(articles);
    }
}
