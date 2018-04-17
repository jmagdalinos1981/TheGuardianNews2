package com.johnmagdalinos.android.newsworld.repository;

import android.arch.lifecycle.LiveData;

import com.johnmagdalinos.android.newsworld.model.database.Article;
import com.johnmagdalinos.android.newsworld.model.database.ArticleDao;
import com.johnmagdalinos.android.newsworld.utilities.NetworkUtils;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Gianni on 16/03/2018.
 */

@Singleton
public class ArticleRepository {
    private NetworkUtils mNetworkUtils;
    private ArticleDao mArticleDao;
    private Executor mExecutor;

    @Inject
    public ArticleRepository(NetworkUtils networkUtils, ArticleDao articleDao) {
        mNetworkUtils = networkUtils;
        mArticleDao = articleDao;
//        mExecutor = executor;
    }

    /** Used to manually sync articles */
    public LiveData<List<Article>> loadArticles(String sectionId) {
        return mArticleDao.loadSectionArticles(sectionId);
    }
}
