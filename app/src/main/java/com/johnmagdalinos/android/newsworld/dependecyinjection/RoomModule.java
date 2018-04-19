package com.johnmagdalinos.android.newsworld.dependecyinjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.johnmagdalinos.android.newsworld.model.database.ArticleDao;
import com.johnmagdalinos.android.newsworld.model.database.ArticleDatabase;
import com.johnmagdalinos.android.newsworld.repository.ArticleRepository;
import com.johnmagdalinos.android.newsworld.utilities.Constants;
import com.johnmagdalinos.android.newsworld.viewmodel.ArticleViewModelFactory;

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
                Constants.DB_NAME)
                .build();
    }

    @Provides
    @Singleton
    ArticleRepository provideArticleRepository(ArticleDao articleDao, SharedPreferences
            sharedPreferences, Application application) {
        return new ArticleRepository(articleDao, sharedPreferences, application);
    }

    @Provides
    @Singleton
    ArticleDao provideArticleDao(ArticleDatabase database) {
        return database.articleDao();
    }

    @Provides
    @Singleton
    ArticleDatabase provideArticleDatabase(Application application) {
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(ArticleRepository repository) {
        return new ArticleViewModelFactory(repository);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
