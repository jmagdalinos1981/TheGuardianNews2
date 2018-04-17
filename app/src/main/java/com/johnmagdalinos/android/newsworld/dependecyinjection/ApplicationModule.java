package com.johnmagdalinos.android.newsworld.dependecyinjection;

import android.app.Application;

import com.johnmagdalinos.android.newsworld.NewsWorldApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(NewsWorldApplication application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }
}
