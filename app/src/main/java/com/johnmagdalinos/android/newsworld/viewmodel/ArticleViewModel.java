package com.johnmagdalinos.android.newsworld.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.johnmagdalinos.android.newsworld.model.database.Article;
import com.johnmagdalinos.android.newsworld.repository.ArticleRepository;
import com.johnmagdalinos.android.newsworld.utilities.Constants;

import java.util.List;

/**
 * Created by Gianni on 15/03/2018.
 */

public class ArticleViewModel extends ViewModel {
    /** Member variables */
    private String mSectionId;
    private LiveData<List<Article>> mArticles;
    private ArticleRepository mArticleRepo;

    public ArticleViewModel(ArticleRepository repo) {
        mArticleRepo = repo;
    }

    public void init(String sectionId) {
        if (mSectionId != null) return;
        mSectionId = sectionId;

        if (mSectionId.equals(Constants.KEY_ALL_NEWS)) {
            mArticles = mArticleRepo.loadAllArticles();
        } else {
            mArticles = mArticleRepo.loadArticles(sectionId);
        }
    }

    public LiveData<List<Article>> getArticles() {
        return mArticles;
    }

}
