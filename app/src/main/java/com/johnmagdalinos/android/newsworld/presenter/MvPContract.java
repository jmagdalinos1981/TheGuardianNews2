package com.johnmagdalinos.android.newsworld.presenter;

/**
 * Contains the interfaces and methods used by the NewsPresenter and the MainFragment in order to
 * allow communication between the two classes
 */

public interface MvPContract {

    /** Passes the data from the Presenter to the View */
    interface BaseView {
        void showNews(String sectionId);

        void showToastMessage(String message);
    }

    /** Passes the input or news to the Presenter */
    interface BasePresenter {
        void inputToPresenter(BaseView baseView, String sectionTitle);

        void newsToPresenter(String sectionId);
    }
}
