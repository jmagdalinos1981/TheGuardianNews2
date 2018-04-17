package com.johnmagdalinos.android.newsworld.model.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Gianni on 08/02/2018.
 */

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(Article... articles);

    @Update
    void updateArticles(Article... articles);

    @Delete
    void deleteArticles(Article... articles);

    @Query("SELECT * FROM articles")
    List<Article> loadAllArticles();

    @Query("SELECT * FROM articles WHERE sectionId LIKE :sectionTitle")
    LiveData<List<Article>> loadSectionArticles(String sectionTitle);

    @Query("DELETE FROM articles")
    void deleteAllArticles();

    @Query("SELECT count(*) FROM articles")
    long countRows();
}
