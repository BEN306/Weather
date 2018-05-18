package com.example.ben.weather.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.ben.weather.MainApplication;
import com.example.ben.weather.R;
import com.example.ben.weather.utility.Utils;


import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public  class MyPreferenceFragment extends PreferenceFragment {

    @Inject
    Utils shared;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication()).getNetComponent().inject(this);
        addPreferencesFromResource(R.xml.settings);
        final ListPreference LanguagePreference = (ListPreference) findPreference("units_list");
        String select_lan = LanguagePreference.getValue();
        if (select_lan.equals("0")) {
            LanguagePreference.setSummary("Fahrenheit");
        } else {
            LanguagePreference.setSummary("Celsius");
        }
        LanguagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.equals("0")) {
                    LanguagePreference.setSummary("Fahrenheit");
                    shared.updateSharedPreference("units","fahrenheit",getActivity());
                    Intent intent=new Intent();
                    getActivity().setResult(2,intent);
                    getActivity().finish();
                } else {
                    LanguagePreference.setSummary("Celsius");
                    shared.updateSharedPreference("units","celsius",getActivity());
                    Intent intent=new Intent();
                    getActivity().setResult(2,intent);
                    getActivity().finish();
                }
                return true;
            }
        });
    }
}