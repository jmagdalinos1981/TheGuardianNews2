package com.johnmagdalinos.android.theguardiannews.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.johnmagdalinos.android.theguardiannews.R;
import com.johnmagdalinos.android.theguardiannews.model.NewsArticle;
import com.johnmagdalinos.android.theguardiannews.presenter.MvPContract.BasePresenter;
import com.johnmagdalinos.android.theguardiannews.presenter.MvPContract.BaseView;
import com.johnmagdalinos.android.theguardiannews.presenter.NewsPresenter;

import java.util.ArrayList;

/**
 * Fragment used to display a list of news articles using a RecyclerView
 */

public class NewsFragment extends Fragment implements BaseView {

    /** Member variables */
    private TextView mTitleTextView;
    private RecyclerView mRecyclerView;
    private String mSectionTitle;
    private BasePresenter mPresenter;
    private NewsAdapter mAdapter;

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

    private static final String TAG = NewsFragment.class.getSimpleName();

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NewsAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

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
    public void showNews(ArrayList<NewsArticle> articles) {
        Log.v(TAG, "News received!");
        mAdapter.swapList(articles);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
