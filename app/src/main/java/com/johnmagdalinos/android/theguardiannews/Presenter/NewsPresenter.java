package com.johnmagdalinos.android.theguardiannews.Presenter;

import android.util.Log;

import com.johnmagdalinos.android.theguardiannews.Model.SampleModel;
import com.johnmagdalinos.android.theguardiannews.Presenter.MvPContract.BaseView;

/**
 * Created by Gianni on 06/02/2018.
 */

public class NewsPresenter implements
        MvPContract.BasePresenter{

    /** Member variables */
    private BaseView mBaseView;
    private SampleModel mModel;

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
    public void newsToPresenter(String news) {
        mBaseView.showNews(news);
        Log.v("NewsPresenter", news);
    }
}
