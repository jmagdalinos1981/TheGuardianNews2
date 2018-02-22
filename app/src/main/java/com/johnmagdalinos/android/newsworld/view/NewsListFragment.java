package com.johnmagdalinos.android.newsworld.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.NewsArticle;
import com.johnmagdalinos.android.newsworld.presenter.MvPContract.BasePresenter;
import com.johnmagdalinos.android.newsworld.presenter.MvPContract.BaseView;
import com.johnmagdalinos.android.newsworld.presenter.NewsPresenter;

import java.util.ArrayList;

/**
 * Fragment used to display a list of news articles using a RecyclerView
 */

public class NewsListFragment extends Fragment implements BaseView, NewsAdapter.NewsAdapterCallback {

    /** Member variables */
    private RecyclerView mRecyclerView;
    private String mSectionTitle;
    private BasePresenter mPresenter;
    private NewsAdapter mAdapter;
    private ArticleCallback mCallback;

    /** Keys for reading and saving the Section Title */
    private static final String KEY_SECTION = "section";

    /** Callback for passing the url to the MainActivity */
    public interface ArticleCallback {
        void onArticleClicked(String url);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ArticleCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ArticleCallback");
        }
    }

    /** Class constructor */
    public static NewsListFragment newInstance(String section) {
        NewsListFragment fragment = new NewsListFragment();
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

        mRecyclerView = rootView.findViewById(R.id.rv_fragment_news);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_drawable);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(drawable);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = new NewsAdapter(this,null);
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
        mAdapter.swapList(articles);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /** Receives the clicked article's url and passes it to the Main Activity */
    @Override
    public void NewsClicked(String url) {
        mCallback.onArticleClicked(url);
    }
}
