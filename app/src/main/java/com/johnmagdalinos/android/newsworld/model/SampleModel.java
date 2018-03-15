package com.johnmagdalinos.android.newsworld.model;

import android.arch.lifecycle.ViewModel;

import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;
import com.johnmagdalinos.android.newsworld.presenter.MvPContract.BasePresenter;

import java.util.ArrayList;

/**
 * Sample Model used to download articles from the API and pass them to the presenter
 */

public class ArticleViewModel extends ViewModel {



    public SampleModel() {
    }

    public void onReceiveSectionTitle(BasePresenter basePresenter, String sectionId) {
        mBasePresenter = basePresenter;
        mBasePresenter.newsToPresenter(sectionId);
//        NewsAsyncTask newsAsyncTask = new NewsAsyncTask();
//
//        newsAsyncTask.setAsyncTaskCallback(this, sectionTitle);
    }

    @Override
    public void result(ArrayList<NewsArticle> articles) {
//        mBasePresenter.newsToPresenter(articles);
    }
}
