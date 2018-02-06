package com.johnmagdalinos.android.theguardiannews.Presenter;

/**
 * Created by Gianni on 06/02/2018.
 */

public interface MvPContract {

    /** Passes the data from the Presenter to the View */
    interface BaseView {
        void setPresenter(BasePresenter presenter);

        void showNews(String newsResult);

        void showToastMessage(String message);
    }

    /** Passes the input to the Presenter */
    interface BasePresenter {
        void inputToPresenter(BaseView baseView, String sectionTitle);

        void newsToPresenter(String news);
    }
}
