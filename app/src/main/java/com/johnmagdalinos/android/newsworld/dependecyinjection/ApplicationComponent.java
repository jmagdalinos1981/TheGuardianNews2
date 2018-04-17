package com.johnmagdalinos.android.newsworld.dependecyinjection;

import android.app.Application;

import com.johnmagdalinos.android.newsworld.view.NewsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {

    void inject(NewsFragment newsFragment);

    Application application();

}
