package com.johnmagdalinos.android.theguardiannews.presenter;

import com.johnmagdalinos.android.theguardiannews.model.NewsArticle;
import com.johnmagdalinos.android.theguardiannews.model.SampleModel;
import com.johnmagdalinos.android.theguardiannews.presenter.MvPContract.BaseView;

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
    public void newsToPresenter(ArrayList<NewsArticle> articles) {
        mBaseView.showNews(articles);
    }
}
