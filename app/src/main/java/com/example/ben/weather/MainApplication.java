package com.example.ben.weather;

import android.app.Application;
import android.content.Context;

import com.example.ben.weather.module.ApiComponent;
import com.example.ben.weather.module.ApiModule;
import com.example.ben.weather.module.AppModule;
import com.example.ben.weather.module.DaggerApiComponent;

public class MainApplication extends Application {
    private static MainApplication mInstance;
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        mInstance = this;
        super.onCreate();
        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }


    public static synchronized MainApplication getInstance() {
        return mInstance;
    }


    public ApiComponent getNetComponent() {
        return mApiComponent;
    }
}