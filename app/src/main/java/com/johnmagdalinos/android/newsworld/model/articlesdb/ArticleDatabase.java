package com.johnmagdalinos.android.newsworld.model.articlesdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Gianni on 08/02/2018.
 */

@Database(entities = {NewsArticle.class}, version = 1)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
