package com.johnmagdalinos.android.newsworld.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.view.adapter.NewsAdapter;
import com.johnmagdalinos.android.newsworld.viewmodel.ArticleViewModel;
import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;
import com.johnmagdalinos.android.newsworld.utilities.Constants;

import java.util.ArrayList;

/**
 * Created by Gianni on 15/03/2018.
 */

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterCallback {
    /** Member variables */
    private ArticleViewModel mViewModel;
    private String mSectionId;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<NewsArticle> mArticles;
    private NewsCallback mCallback;

    public interface NewsCallback {
        void onNewsClicked(String url);
    }

    public static NewsFragment newInstance(String sectionId) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_SECTION_ID, sectionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (NewsCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NewsCallback");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionId = getArguments().getString(Constants.KEY_SECTION_ID);
        mViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        mViewModel.init(mArticles);
        mViewModel.getArticles().observe(this, new Observer<ArrayList<NewsArticle>>() {
            @Override
            public void onChanged(@Nullable ArrayList<NewsArticle> newsArticles) {
                mNewsAdapter.swapList(newsArticles);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mRecyclerView = view.findViewById(R.id.rv_news_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_drawable);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(drawable);
        mRecyclerView.addItemDecoration(itemDecoration);
        mNewsAdapter = new NewsAdapter(this,null);
        mRecyclerView.setAdapter(mNewsAdapter);
        return view;
    }

    /** Passes the clicked article's url to the activity */
    @Override
    public void NewsClicked(String url) {
        mCallback.onNewsClicked(url);
    }
}
