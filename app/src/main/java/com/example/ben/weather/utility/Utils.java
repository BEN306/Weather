package com.example.ben.weather.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;


import javax.inject.Inject;

public class Utils {
    private SharedPreferences sharedPreferences;

    @Inject
    public Utils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static boolean isOnline(Context  activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnected();
    }

    private void initializeSharedPreference(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void updateSharedPreference(String key, String value,Context context) {
        if (sharedPreferences == null) {
            initializeSharedPreference(context);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void updateSharedPreference(String key, int value,Context context) {
        if (sharedPreferences == null) {
            initializeSharedPreference(context);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getSharedPreferenceValue(String key,Context context) {
        if (sharedPreferences == null) {
            initializeSharedPreference(context);
        }
        if (key != null) {
            return sharedPreferences.getString(key, null);
        } else {
            return null;
        }
    }

    public int getSharedPreferenceValueInt(String key,Context context) {
        if (sharedPreferences == null) {
            initializeSharedPreference(context);
        }
        if (key != null) {
            return sharedPreferences.getInt(key, 0);
        } else {
            return 0;
        }
    }
}