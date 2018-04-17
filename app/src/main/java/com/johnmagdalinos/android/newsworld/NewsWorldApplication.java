package com.johnmagdalinos.android.newsworld;

import android.app.Application;

import com.johnmagdalinos.android.newsworld.dependecyinjection.ApplicationComponent;
import com.johnmagdalinos.android.newsworld.dependecyinjection.ApplicationModule;
import com.johnmagdalinos.android.newsworld.dependecyinjection.DaggerApplicationComponent;
import com.johnmagdalinos.android.newsworld.dependecyinjection.RoomModule;

public class NewsWorldApplication extends Application {
    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();

    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
