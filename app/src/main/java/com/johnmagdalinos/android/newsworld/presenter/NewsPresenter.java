package com.johnmagdalinos.android.newsworld.presenter;

import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;
import com.johnmagdalinos.android.newsworld.model.SampleModel;
import com.johnmagdalinos.android.newsworld.presenter.MvPContract.BaseView;

import java.util.ArrayList;

/**
 * Presenter bridging the view with the model.
 * Takes as input the news section and returns the news articles
 */

public class NewsPresenter implements
        MvPContract.BasePresenter {

    /** Member variables */
    private BaseView mBaseView;
    private SampleModel mModel;

    /** Public constructor */
    public NewsPresenter() {
        mModel = new SampleModel();
    }

    /** Receives input from the View */
    @Override
    public void inputToPresenter(BaseView baseView, String sectionTitle) {
        mBaseView = baseView;
        mModel.onReceiveSectionTitle(this, sectionTitle);
    }

    /** Receives news from model */
    @Override
    public void newsToPresenter(String sectionId) {
        mBaseView.showNews(sectionId);
    }
}
