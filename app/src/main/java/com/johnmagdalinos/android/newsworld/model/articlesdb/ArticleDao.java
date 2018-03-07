package com.johnmagdalinos.android.newsworld.model.articlesdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by Gianni on 08/02/2018.
 */

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(NewsArticle... newsArticles);

    @Update
    void updateArticles(NewsArticle... newsArticles);

    @Delete
    void deleteArticles(NewsArticle... newsArticles);

    @Query("SELECT * FROM articles")
    NewsArticle[] loadAllArticles();

    @Query("DELETE FROM articles")
    void deleteAllArticles();
}
