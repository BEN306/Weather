package com.example.ben.weather.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.example.ben.weather.MainApplication;
import com.example.ben.weather.fragment.MyPreferenceFragment;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        ((MainApplication) getApplication()).getNetComponent().inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}