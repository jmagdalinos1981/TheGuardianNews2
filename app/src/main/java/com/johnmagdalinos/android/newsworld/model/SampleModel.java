package com.johnmagdalinos.android.newsworld.model;

import com.johnmagdalinos.android.newsworld.presenter.MvPContract.BasePresenter;
import com.johnmagdalinos.android.newsworld.utilities.NewsAsyncTask;

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

        newsAsyncTask.setAsyncTaskCallback(this, sectionTitle);
    }

    @Override
    public void result(ArrayList<NewsArticle> articles) {
        mBasePresenter.newsToPresenter(articles);
    }
}
