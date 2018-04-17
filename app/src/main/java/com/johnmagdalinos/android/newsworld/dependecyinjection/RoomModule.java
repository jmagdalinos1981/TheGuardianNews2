package com.johnmagdalinos.android.newsworld.dependecyinjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.johnmagdalinos.android.newsworld.model.database.ArticleDao;
import com.johnmagdalinos.android.newsworld.model.database.ArticleDatabase;
import com.johnmagdalinos.android.newsworld.repository.ArticleRepository;
import com.johnmagdalinos.android.newsworld.utilities.NetworkUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private final ArticleDatabase database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                ArticleDatabase.class,
                "database.db")
                .build();
    }

    @Provides
    @Singleton
    ArticleRepository provideArticleRepository(NetworkUtils networkUtils, ArticleDao articleDao) {
        return new ArticleRepository(networkUtils, articleDao);
    }

    @Provides
    @Singleton
    NetworkUtils provideNetworkUtils() {
        return new NetworkUtils();
    }

    @Provides
    @Singleton
    ArticleDao provideArticleDao(ArticleDatabase database) {
        return database.articleDao();
    }
}
