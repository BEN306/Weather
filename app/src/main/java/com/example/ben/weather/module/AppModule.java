package com.example.ben.weather.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    @Inject
    SharedPreferences getAppPreferences() {
        String PREF_NAME = "prefs";
        return mApplication.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}