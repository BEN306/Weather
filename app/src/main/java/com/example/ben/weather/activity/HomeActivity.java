package com.example.ben.weather.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Surface;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ben.weather.MainApplication;
import com.example.ben.weather.R;
import com.example.ben.weather.adapter.WeatherAdapter;
import com.example.ben.weather.deo.Wether;
import com.example.ben.weather.fragment.WeatherFragment;
import com.example.ben.weather.utility.APIinterface;
import com.example.ben.weather.utility.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class HomeActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;

    @Inject
    Utils SharedPref;
    private List<Wether.ListWether> listWetherList;
    private String units = "fahrenheit";
    @BindView(R.id.tv_date)
    TextView mTvdate;

    @BindView(R.id.tv_temp1)
    TextView mTvtemp1;
    @BindView(R.id.tv_weather_details1)
    TextView mTvWeather1;
    @BindView(R.id.weather_icon1)
    ImageView mTvWeatherIcon1;

    @BindView(R.id.tv_temp2)
    TextView mTvtemp2;
    @BindView(R.id.tv_weather_details2)
    TextView mTvWeather2;
    @BindView(R.id.weather_icon2)
    ImageView mTvWeatherIcon2;

    @BindView(R.id.tv_temp3)
    TextView mTvtemp3;
    @BindView(R.id.tv_weather_details3)
    TextView mTvWeather3;
    @BindView(R.id.weather_icon3)
    ImageView mTvWeatherIcon3;

    @BindView(R.id.tv_temp4)
    TextView mTvtemp4;
    @BindView(R.id.tv_weather_details4)
    TextView mTvWeather4;
    @BindView(R.id.weather_icon4)
    ImageView mTvWeatherIcon4;


    @BindView(R.id.tv_cityName1)
    TextView mTvCityName1;
    @BindView(R.id.tv_cityName2)
    TextView mTvCityName2;
    @BindView(R.id.tv_cityName3)
    TextView mTvCityName3;
    @BindView(R.id.tv_cityName4)
    TextView mTvCityName4;
    @BindView(R.id.tv_max1)
    TextView mTvMax1;
    @BindView(R.id.tv_max2)
    TextView mTvMax2;
    @BindView(R.id.tv_max3)
    TextView mTvMax3;
    @BindView(R.id.tv_max4)
    TextView mTvMax4;
    @BindView(R.id.tv_min1)
    TextView mTvMin1;
    @BindView(R.id.tv_min2)
    TextView mTvMin2;
    @BindView(R.id.tv_min3)
    TextView mTvMin3;
    @BindView(R.id.tv_min4)
    TextView mTvMin4;
    @BindView(R.id.rv_recycler1)
    RecyclerView mRvRecycler1;
    @BindView(R.id.rv_recycler2)
    RecyclerView mRvRecycler2;
    @BindView(R.id.rv_recycler3)
    RecyclerView mRvRecycler3;
    @BindView(R.id.rv_recycler4)
    RecyclerView mRvRecycler4;

    @BindView(R.id.chennai_view)
    CardView mChennaiView;
    @BindView(R.id.mumbai_view)
    CardView mMumbaiView;
    @BindView(R.id.bangalore_view)
    CardView mBangaloreView;
    @BindView(R.id.newDelhi_view)
    CardView mNewDelhiView;
    LinearLayoutManager mLayoutManager1, mLayoutManager2, mLayoutManager3, mLayoutManager4, mLayoutManager5, mLayoutManager6, mLayoutManager7, mLayoutManager8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        ((MainApplication) getApplication()).getNetComponent().inject(this);
        units = SharedPref.getSharedPreferenceValue("units", getApplicationContext());
        if (units == null) {
            units = "fahrenheit";
            SharedPref.updateSharedPreference("units", units, getApplicationContext());
        }

        int rot = getRotation(this);
        mLayoutManager1 = new LinearLayoutManager(this);
        mLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager3 = new LinearLayoutManager(this);
        mLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager4 = new LinearLayoutManager(this);
        mLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);

        mLayoutManager5 = new LinearLayoutManager(this);
        mLayoutManager5.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager6 = new LinearLayoutManager(this);
        mLayoutManager6.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager7 = new LinearLayoutManager(this);
        mLayoutManager7.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager8 = new LinearLayoutManager(this);
        mLayoutManager8.setOrientation(LinearLayoutManager.VERTICAL);

        if (rot == 0) {
            mRvRecycler1.setLayoutManager(mLayoutManager1);
            mRvRecycler2.setLayoutManager(mLayoutManager2);
            mRvRecycler3.setLayoutManager(mLayoutManager3);
            mRvRecycler4.setLayoutManager(mLayoutManager4);
        } else {
            mRvRecycler1.setLayoutManager(mLayoutManager5);
            mRvRecycler2.setLayoutManager(mLayoutManager6);
            mRvRecycler3.setLayoutManager(mLayoutManager7);
            mRvRecycler4.setLayoutManager(mLayoutManager8);
        }

        getDate();
        Wether("Chennai");

    }

    private void getDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        mTvdate.setText(formattedDate);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    private void Wether(final String city) {
        System.out.println("city===>" + city);
        APIinterface apIinterface = retrofit.create(APIinterface.class);
        units = SharedPref.getSharedPreferenceValue("units", getApplicationContext());
        if (units.equals("fahrenheit")) {
            units = "imperial";
        } else {
            units = "metric";
        }
        Call<Wether> call = apIinterface.getWether(city, units);
        System.out.println("URL===>" + call.request().url());
        call.enqueue(new Callback<Wether>() {
            @Override
            public void onResponse(@NonNull Call<Wether> call, @NonNull Response<Wether> response) {
                try {
                    Wether wether = response.body();
                    if (response.code() == 404) {

                    } else {
                        if (wether != null) {
                            listWetherList = wether.getList();
                            switch (city) {
                                case "Chennai":
                                    bindChennai(listWetherList);
                                    Wether("Mumbai");
                                    break;
                                case "Mumbai":
                                    bindMumbai(listWetherList);
                                    Wether("Bangalore");
                                    break;
                                case "Bangalore":
                                    bindBangalore(listWetherList);
                                    Wether("New Delhi");
                                    break;
                                case "New Delhi":
                                    bindNewDelhi(listWetherList);
                                    break;
                                default:
                                    FragmentManager manager = getSupportFragmentManager();
                                    WeatherFragment editNameDialog = WeatherFragment.getInstance(listWetherList.get(0), city);
                                    editNameDialog.show(manager, "Weather");
                                    break;
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Wether> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void MyWether(final String city) {

        APIinterface apIinterface = retrofit.create(APIinterface.class);
        units = SharedPref.getSharedPreferenceValue("units", getApplicationContext());
        if (units.equals("fahrenheit")) {
            units = "imperial";
        } else {
            units = "metric";
        }
        Call<Wether> call = apIinterface.getWether(city, units);
        System.out.println("URL===>" + call.request().url());
        call.enqueue(new Callback<Wether>() {
            @Override
            public void onResponse(@NonNull Call<Wether> call, @NonNull Response<Wether> response) {
                try {
                    Wether wether = response.body();
                    if (response.code() == 404) {

                    } else {
                        if (wether != null) {
                            listWetherList = wether.getList();
                            FragmentManager manager = getSupportFragmentManager();
                            WeatherFragment editNameDialog = WeatherFragment.getInstance(listWetherList.get(0), city);
                            editNameDialog.show(manager, "Weather");


                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Wether> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    void bindChennai(final List<Wether.ListWether> currentWetherlist) {
        final Wether.ListWether listWether = currentWetherlist.get(0);
        mTvCityName1.setText("Chennai");
        double fhigh = Double.parseDouble(listWether.getMain().getTemp_max());
        //fhigh = ((fhigh - 32) * .5556);
        int textSize3 = getResources().getDimensionPixelSize(R.dimen.text_size_3);
        int textSize6 = getResources().getDimensionPixelSize(R.dimen.text_size_1);
        SpannableString span1 = new SpannableString(String.valueOf(Math.round(fhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence finalText = TextUtils.concat(span1);
        mTvMax1.setText(finalText);
        // mTvMax1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_android, 0, 0, 0);

        Drawable img = getResources().getDrawable(R.drawable.ic_arrow_drop_up_android);
        img.setBounds(0, 0, 90, 90);
        mTvMax1.setCompoundDrawables(img, null, null, null);


        double mhigh = Double.parseDouble(listWether.getMain().getTemp_min());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvMin1.setText(finalText);

        img = getResources().getDrawable(R.drawable.ic_arrow_drop_down_android);
        img.setBounds(0, 0, 90, 90);
        mTvMin1.setCompoundDrawables(img, null, null, null);

        mhigh = Double.parseDouble(listWether.getMain().getTemp());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize6), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvtemp1.setText(finalText);

        String icon = "http://openweathermap.org/img/w/" + listWether.getWeather().get(0).getIcon() + ".png";
        if (listWether.getWeather().get(0).getIcon() != null && !listWether.getWeather().get(0).getIcon().isEmpty()) {
            Picasso.with(this).load(icon).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(mTvWeatherIcon1);
            //weather_icon.setColorFilter(ContextCompat.getColor(activity, R.color.weather_icon_color));
        }

        mTvWeather1.setText(listWether.getWeather().get(0).getMain());

        mChennaiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                WeatherFragment editNameDialog = WeatherFragment.getInstance(listWether, "Chennai");
                editNameDialog.show(manager, "Weather");
            }
        });
        List<Wether.ListWether> currentWetherlist1 = new ArrayList<>();
        currentWetherlist1 = currentWetherlist;
        currentWetherlist1.remove(0);
        WeatherAdapter weatherAdapter = new WeatherAdapter(this, currentWetherlist1, "Chennai");
        mRvRecycler1.setAdapter(weatherAdapter);


    }

    void bindMumbai(final List<Wether.ListWether> currentWetherlist) {
        final Wether.ListWether listWether = currentWetherlist.get(0);
        mTvCityName2.setText("Mumbai");
        double fhigh = Double.parseDouble(listWether.getMain().getTemp_max());
        //fhigh = ((fhigh - 32) * .5556);
        int textSize3 = getResources().getDimensionPixelSize(R.dimen.text_size_3);
        int textSize6 = getResources().getDimensionPixelSize(R.dimen.text_size_6);
        SpannableString span1 = new SpannableString(String.valueOf(Math.round(fhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence finalText = TextUtils.concat(span1);
        mTvMax2.setText(finalText);


        Drawable img = getResources().getDrawable(R.drawable.ic_arrow_drop_up_android);
        img.setBounds(0, 0, 90, 90);
        mTvMax2.setCompoundDrawables(img, null, null, null);


        double mhigh = Double.parseDouble(listWether.getMain().getTemp_min());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvMin2.setText(finalText);

        img = getResources().getDrawable(R.drawable.ic_arrow_drop_down_android);
        img.setBounds(0, 0, 90, 90);
        mTvMin2.setCompoundDrawables(img, null, null, null);

        mhigh = Double.parseDouble(listWether.getMain().getTemp());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize6), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvtemp2.setText(finalText);

        String icon = "http://openweathermap.org/img/w/" + listWether.getWeather().get(0).getIcon() + ".png";
        if (listWether.getWeather().get(0).getIcon() != null && !listWether.getWeather().get(0).getIcon().isEmpty()) {
            Picasso.with(this).load(icon).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(mTvWeatherIcon2);
            //weather_icon.setColorFilter(ContextCompat.getColor(activity, R.color.weather_icon_color));
        }

        mTvWeather2.setText(listWether.getWeather().get(0).getMain());

        mMumbaiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                WeatherFragment editNameDialog = WeatherFragment.getInstance(listWether, "Mumbai");
                editNameDialog.show(manager, "Weather");
            }
        });
        List<Wether.ListWether> currentWetherlist1 = new ArrayList<>();
        currentWetherlist1 = currentWetherlist;
        currentWetherlist1.remove(0);
        WeatherAdapter weatherAdapter = new WeatherAdapter(this, currentWetherlist1, "Mumbai");
        mRvRecycler2.setAdapter(weatherAdapter);

    }

    void bindBangalore(final List<Wether.ListWether> currentWetherlist) {
        final Wether.ListWether listWether = currentWetherlist.get(0);
        mTvCityName3.setText("Bangalore");
        double fhigh = Double.parseDouble(listWether.getMain().getTemp_max());
        //fhigh = ((fhigh - 32) * .5556);
        int textSize3 = getResources().getDimensionPixelSize(R.dimen.text_size_3);
        int textSize6 = getResources().getDimensionPixelSize(R.dimen.text_size_6);
        SpannableString span1 = new SpannableString(String.valueOf(Math.round(fhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence finalText = TextUtils.concat(span1);
        mTvMax3.setText(finalText);
        // mTvMax1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_android, 0, 0, 0);

        Drawable img = getResources().getDrawable(R.drawable.ic_arrow_drop_up_android);
        img.setBounds(0, 0, 90, 90);
        mTvMax3.setCompoundDrawables(img, null, null, null);


        double mhigh = Double.parseDouble(listWether.getMain().getTemp_min());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvMin3.setText(finalText);

        img = getResources().getDrawable(R.drawable.ic_arrow_drop_down_android);
        img.setBounds(0, 0, 90, 90);
        mTvMin3.setCompoundDrawables(img, null, null, null);

        mhigh = Double.parseDouble(listWether.getMain().getTemp());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize6), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvtemp3.setText(finalText);

        String icon = "http://openweathermap.org/img/w/" + currentWetherlist.get(0).getWeather().get(0).getIcon() + ".png";
        if (listWether.getWeather().get(0).getIcon() != null && !listWether.getWeather().get(0).getIcon().isEmpty()) {
            Picasso.with(this).load(icon).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(mTvWeatherIcon3);
            //weather_icon.setColorFilter(ContextCompat.getColor(activity, R.color.weather_icon_color));
        }

        mTvWeather3.setText(listWether.getWeather().get(0).getMain());

        mBangaloreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                WeatherFragment editNameDialog = WeatherFragment.getInstance(listWether, "Bangalore");
                editNameDialog.show(manager, "Weather");
            }
        });

        List<Wether.ListWether> currentWetherlist1 = new ArrayList<>();
        currentWetherlist1 = currentWetherlist;
        currentWetherlist1.remove(0);
        WeatherAdapter weatherAdapter = new WeatherAdapter(this, currentWetherlist1, "Bangalore");
        mRvRecycler3.setAdapter(weatherAdapter);

    }

    void bindNewDelhi(final List<Wether.ListWether> currentWetherlist) {
        final Wether.ListWether listWether = currentWetherlist.get(0);
        mTvCityName4.setText("New Delhi");
        double fhigh = Double.parseDouble(listWether.getMain().getTemp_max());
        //fhigh = ((fhigh - 32) * .5556);
        int textSize3 = getResources().getDimensionPixelSize(R.dimen.text_size_3);
        int textSize6 = getResources().getDimensionPixelSize(R.dimen.text_size_6);
        SpannableString span1 = new SpannableString(String.valueOf(Math.round(fhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence finalText = TextUtils.concat(span1);
        mTvMax4.setText(finalText);
        // mTvMax1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_android, 0, 0, 0);

        Drawable img = getResources().getDrawable(R.drawable.ic_arrow_drop_up_android);
        img.setBounds(0, 0, 90, 90);
        mTvMax4.setCompoundDrawables(img, null, null, null);


        double mhigh = Double.parseDouble(listWether.getMain().getTemp_min());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvMin4.setText(finalText);

        img = getResources().getDrawable(R.drawable.ic_arrow_drop_down_android);
        img.setBounds(0, 0, 90, 90);
        mTvMin4.setCompoundDrawables(img, null, null, null);

        mhigh = Double.parseDouble(listWether.getMain().getTemp());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize6), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        mTvtemp4.setText(finalText);

        String icon = "http://openweathermap.org/img/w/" + listWether.getWeather().get(0).getIcon() + ".png";
        if (listWether.getWeather().get(0).getIcon() != null && !listWether.getWeather().get(0).getIcon().isEmpty()) {
            Picasso.with(this).load(icon).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(mTvWeatherIcon4);
            //weather_icon.setColorFilter(ContextCompat.getColor(activity, R.color.weather_icon_color));
        }

        mTvWeather4.setText(listWether.getWeather().get(0).getMain());
        mNewDelhiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                WeatherFragment editNameDialog = WeatherFragment.getInstance(listWether, "New Delhi");
                editNameDialog.show(manager, "Weather");
            }
        });

        List<Wether.ListWether> currentWetherlist1 = new ArrayList<>();
        currentWetherlist1 = currentWetherlist;
        currentWetherlist1.remove(0);
        WeatherAdapter weatherAdapter = new WeatherAdapter(this, currentWetherlist1, "New Delhi");
        mRvRecycler4.setAdapter(weatherAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivityForResult(intent, 2);
            return true;
        } else if (id == R.id.action_location) {
            turnGPSOn();
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                20);
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                20);
                    }
                } else {
                    getMyCurrentLocation();
                }
            } else {
                getMyCurrentLocation();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getMyCurrentLocation();
        }
    }

    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    Location location;

    Double MyLat, MyLong;
    String CityName = "";
    String StateName = "";
    String CountryName = "";

    void getMyCurrentLocation() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

        }


        if (gps_enabled) {
            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        }


        if (network_enabled && location == null) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

        }


        if (network_enabled && location == null) {
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }

        if (location != null) {

            MyLat = location.getLatitude();
            MyLong = location.getLongitude();


        } else {
            Location loc = getLastKnownLocation(this);
            if (loc != null) {

                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();


            }
        }
        locManager.removeUpdates(locListener);
        try {

            System.out.println("MyLat===>" + MyLat);
            Geocoder geocoder;

            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);

            StateName = addresses.get(0).getAdminArea();
            CityName = addresses.get(0).getLocality();
            CountryName = addresses.get(0).getCountryName();
            // you can get more details other than this . like country code, state code, etc.


            System.out.println(" StateName \n" + StateName);
            System.out.println(" CityName \n" + CityName);
            System.out.println(" CountryName \n" + CountryName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyWether(CityName);
    }

    public static Location getLastKnownLocation(Context context) {
        Location location = null;
        @SuppressLint("WrongConstant") LocationManager locationmanager = (LocationManager) context.getSystemService("location");
        List list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext())
                break;
            String s = (String) iterator.next();

            if (i != false && !locationmanager.isProviderEnabled(s))
                continue;

            @SuppressLint("MissingPermission") Location location1 = locationmanager.getLastKnownLocation(s);
            if (location1 == null)
                continue;
            if (location != null) {

                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if (f >= f1) {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if (l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
            i = locationmanager.isProviderEnabled(s);
        } while (true);
        return location;
    }

    public void turnGPSOn() {
        try {

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            Wether("Chennai");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;

        switch (orientation) {

            case Configuration.ORIENTATION_LANDSCAPE:

                mRvRecycler1.setLayoutManager(mLayoutManager5);
                mRvRecycler2.setLayoutManager(mLayoutManager6);
                mRvRecycler3.setLayoutManager(mLayoutManager7);
                mRvRecycler4.setLayoutManager(mLayoutManager8);

                break;

            case Configuration.ORIENTATION_PORTRAIT:

                mRvRecycler1.setLayoutManager(mLayoutManager1);
                mRvRecycler2.setLayoutManager(mLayoutManager2);
                mRvRecycler3.setLayoutManager(mLayoutManager3);
                mRvRecycler4.setLayoutManager(mLayoutManager4);
                break;

        }
    }

    public int getRotation(Context context) {
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 1;
            case Surface.ROTATION_180:
                return 0;
            default:
                return 1;
        }
    }

}
