package com.johnmagdalinos.android.newsworld.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Gianni on 08/02/2018.
 */

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
