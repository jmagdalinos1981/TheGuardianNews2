package com.johnmagdalinos.android.theguardiannews.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.johnmagdalinos.android.theguardiannews.Presenter.MvPContract.BasePresenter;
import com.johnmagdalinos.android.theguardiannews.Presenter.MvPContract.BaseView;
import com.johnmagdalinos.android.theguardiannews.Presenter.NewsPresenter;
import com.johnmagdalinos.android.theguardiannews.R;

/**
 * Fragment used to display a list of news articles using a RecyclerView
 */

public class NewsFragment extends Fragment implements BaseView {

    /** Member variables */
    private TextView mTitleTextView;
    private RecyclerView mRecyclerView;
    private String mSectionTitle;
    private BasePresenter mPresenter;

    /** Keys for reading and saving the Section Title */
    private static final String KEY_SECTION = "section";

    /** Public constructor */
    public static NewsFragment newInstance(String section) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SECTION, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news,container, false);

        if (savedInstanceState != null) {
            mSectionTitle = savedInstanceState.getString(KEY_SECTION);
        } else {
            mSectionTitle = getArguments().getString(KEY_SECTION);
        }

        mTitleTextView = rootView.findViewById(R.id.tv_fragment_news);
        mRecyclerView = rootView.findViewById(R.id.rv_fragment_news);

        if (mPresenter == null) {
            mPresenter = new NewsPresenter();
        }
        mPresenter.inputToPresenter(this, mSectionTitle);

        return rootView;
    }

    /** Save the Section Title */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SECTION, mSectionTitle);
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNews(String title) {
        mTitleTextView.setText(title);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
