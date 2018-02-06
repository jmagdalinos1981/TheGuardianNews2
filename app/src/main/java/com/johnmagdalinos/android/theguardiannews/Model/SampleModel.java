package com.johnmagdalinos.android.theguardiannews.Model;

import com.johnmagdalinos.android.theguardiannews.Presenter.MvPContract.BasePresenter;

/**
 * Created by Gianni on 06/02/2018.
 */

public class SampleModel {

    private BasePresenter mBasePresenter;

    public SampleModel() {
    }

    public void onReceiveSectionTitle(BasePresenter basePresenter, String sectionTitle) {
        String finalResult = "This is the section you selected: " + sectionTitle;

        mBasePresenter = basePresenter;
        mBasePresenter.newsToPresenter(finalResult);
    }
}
