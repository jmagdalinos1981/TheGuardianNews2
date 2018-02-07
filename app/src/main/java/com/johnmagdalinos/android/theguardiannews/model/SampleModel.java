package com.johnmagdalinos.android.theguardiannews.model;

import com.johnmagdalinos.android.theguardiannews.presenter.MvPContract.BasePresenter;
import com.johnmagdalinos.android.theguardiannews.utilities.NewsAsyncTask;

import java.util.ArrayList;

/**
 * Sample Model used to download articles from the API and pass them to the presenter
 */

public class SampleModel implements NewsAsyncTask.onTaskCompletedCallback {

    private BasePresenter mBasePresenter;

    public SampleModel() {
    }

    public void onReceiveSectionTitle(BasePresenter basePresenter, String sectionTitle) {
        mBasePresenter = basePresenter;
        NewsAsyncTask newsAsyncTask = new NewsAsyncTask();

        newsAsyncTask.setAsyncTaskCallback(sectionTitle, this);
    }

    @Override
    public void result(ArrayList<NewsArticle> articles) {
        mBasePresenter.newsToPresenter(articles);
    }
}
