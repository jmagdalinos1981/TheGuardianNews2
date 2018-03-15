package com.johnmagdalinos.android.newsworld.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;

import java.util.ArrayList;

/**
 * Created by Gianni on 15/03/2018.
 */

public class ArticleViewModel extends ViewModel {
    private LiveData<ArrayList<NewsArticle>> mArticles;

    public void init(ArrayList<NewsArticle> articles) {
        mArticles = articles;
    }

    public LiveData<ArrayList<NewsArticle>> getArticles() {
        return mArticles;
    }

}
