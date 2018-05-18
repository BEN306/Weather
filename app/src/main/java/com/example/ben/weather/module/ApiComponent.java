package com.example.ben.weather.module;
import com.example.ben.weather.activity.HomeActivity;
import com.example.ben.weather.activity.SettingsActivity;
import com.example.ben.weather.fragment.MyPreferenceFragment;
import com.example.ben.weather.fragment.WeatherFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class })
public interface ApiComponent {
    void inject(HomeActivity activity);
    void inject(SettingsActivity activity);
    void inject(MyPreferenceFragment activity);
    void inject(WeatherFragment fragment);
}