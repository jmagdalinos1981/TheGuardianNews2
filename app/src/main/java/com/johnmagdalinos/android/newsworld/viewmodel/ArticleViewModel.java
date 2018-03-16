package com.johnmagdalinos.android.newsworld.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.johnmagdalinos.android.newsworld.model.articlesdb.Article;
import com.johnmagdalinos.android.newsworld.repository.ArticleRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Gianni on 15/03/2018.
 */

public class ArticleViewModel extends ViewModel {
    /** Member variables */
    private String mSectionId;
    private LiveData<List<Article>> mArticles;
    private ArticleRepository mArticleRepo;

    public ArticleViewModel(){}

    @Inject
    public ArticleViewModel(ArticleRepository repo) {
        mArticleRepo = repo;
    }

    @Inject
    public void init(String sectionId) {
        if (mSectionId != null) return;
        mSectionId = sectionId;
        mArticleRepo = repo;

        mArticles = mArticleRepo.syncArticles(sectionId);
    }

    public LiveData<List<Article>> getArticles() {
        return mArticles;
    }

}
