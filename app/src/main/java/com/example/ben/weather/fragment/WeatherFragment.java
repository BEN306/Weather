package com.example.ben.weather.fragment;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.os.BuildCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ben.weather.MainApplication;
import com.example.ben.weather.R;
import com.example.ben.weather.deo.Wether;
import com.example.ben.weather.utility.APIinterface;
import com.example.ben.weather.utility.Utils;
import com.squareup.picasso.Picasso;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

@SuppressLint("ValidFragment")
public class WeatherFragment extends DialogFragment {

    private static Wether.ListWether listWetherList;

    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.weather)
    TextView weather;
    @BindView(R.id.pressure)
    TextView pressure;
    @BindView(R.id.Wind)
    TextView Wind;
    @BindView(R.id.uv)
    TextView uv;
    @BindView(R.id.currenttemp)
    TextView currenttemp;
    @BindView(R.id.no_data)
    LinearLayout nodata;
    @BindView(R.id.constraint)
    LinearLayout constraint;

    @BindView(R.id.data1)
    TextView sunrise_txt;

    @BindView(R.id.pro)
    ProgressBar progressBar;

    @BindView(R.id.weather_icon)
    ImageView weather_icon;

    @BindView(R.id.share)
    ImageView share;



    @Inject
    Retrofit retrofit;


    private AppCompatActivity activity;

    @Inject
    Utils SharedPref;
    static WeatherFragment weatherFragment;
    static String place_txt;

    @SuppressLint("ValidFragment")
    public WeatherFragment() {
    }
    public static WeatherFragment getInstance(Wether.ListWether currentWetherlist, String place)
    {
        listWetherList=currentWetherlist;
        place_txt=place;
        if(weatherFragment==null)
        {
            weatherFragment=new WeatherFragment();
        }
        return weatherFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.GONE);
        ((MainApplication) activity.getApplication()).getNetComponent().inject(this);
        Wether();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "https://play.google.com/store/apps/details?id=com.example.ben.weather";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String location = "Location : "+place_txt;
                Double mhigh = Double.parseDouble(listWetherList.getMain().getTemp());
                String Temperature ="Temperature : "+ String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C";
                mhigh = Double.parseDouble(listWetherList.getMain().getTemp_max());
                String max = "High : "+ String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C";
                mhigh = Double.parseDouble(listWetherList.getMain().getTemp_min());
                String min = "Low  : "+ String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C";
                shareBody = shareBody + "\n" + location + "\n" + Temperature+ "\n" + max+ "\n" + min;
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(sharingIntent);

            }
        });
        return view;
    }




    private void Wether() {
        int textSize1 = getResources().getDimensionPixelSize(R.dimen.text_size_1);
        int textSize2 = getResources().getDimensionPixelSize(R.dimen.text_size_2);
        int textSize3 = getResources().getDimensionPixelSize(R.dimen.text_size_3);
        int textSize4 = getResources().getDimensionPixelSize(R.dimen.text_size_4);
        int textSize5 = getResources().getDimensionPixelSize(R.dimen.text_size_5);
        int textSize6 = getResources().getDimensionPixelSize(R.dimen.text_size_6);

        //Place
        String text1 = place_txt;
        SpannableString span1 = new SpannableString(text1);
        span1.setSpan(new AbsoluteSizeSpan(textSize1), 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, text1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        CharSequence finalText = TextUtils.concat(span1);
        place.setText(finalText);

        //Pressure
        //double v = Double.parseDouble(main.getPressure());
        //v = v / 33.863886666667;
        ///String presure = String.format("%.2f", v, null);
        String presure=listWetherList.getMain().getPressure();
        span1 = new SpannableString(getResources().getString(R.string.pressure));
        span1.setSpan(new AbsoluteSizeSpan(textSize5), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        SpannableString span2 = new SpannableString(presure);
        span2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span2.setSpan(new AbsoluteSizeSpan(textSize6), 0, span2.length(), SPAN_INCLUSIVE_INCLUSIVE);
        SpannableString span3 = new SpannableString(" in");
        span3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span3.setSpan(new AbsoluteSizeSpan(textSize5), 0, span3.length(), SPAN_INCLUSIVE_INCLUSIVE);
        finalText = TextUtils.concat(span1, "\n", span2, span3);
        pressure.setText(finalText);


        //wind
        double v = Double.parseDouble(listWetherList.getWind().getSpeed());
        v = v * 1.60934;
        String wind_str = "SW " + String.format("%.2f", v);
        span1 = new SpannableString(getResources().getString(R.string.wind));
        span1.setSpan(new AbsoluteSizeSpan(textSize5), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span2 = new SpannableString(wind_str);
        span2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span2.setSpan(new AbsoluteSizeSpan(textSize6), 0, span2.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span3 = new SpannableString(" km/h");
        span3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span3.setSpan(new AbsoluteSizeSpan(textSize5), 0, span3.length(), SPAN_INCLUSIVE_INCLUSIVE);
        finalText = TextUtils.concat(span1, "\n", span2, span3);
        Wind.setText(finalText);

        //humidity
        span1 = new SpannableString(getResources().getString(R.string.humidity));
        span1.setSpan(new AbsoluteSizeSpan(textSize5), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span2 = new SpannableString(listWetherList.getMain().getHumidity());
        span2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span2.setSpan(new AbsoluteSizeSpan(textSize6), 0, span2.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span3 = new SpannableString(" %");
        span3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span3.setSpan(new AbsoluteSizeSpan(textSize5), 0, span3.length(), SPAN_INCLUSIVE_INCLUSIVE);
        finalText = TextUtils.concat(span1, "\n", span2, span3);
        uv.setText(finalText);

        //Date
        String sdate = listWetherList.getDt_txt();
        span1 = new SpannableString(sdate);
        String[] s = sdate.split(" ");
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, s[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(new AbsoluteSizeSpan(textSize3), 0, sdate.length(), SPAN_INCLUSIVE_INCLUSIVE);
        date.setText(span1);

        //WeatherIcon

        String icon = "http://openweathermap.org/img/w/" + listWetherList.getWeather().get(0).getIcon() + ".png";
        if (listWetherList.getWeather().get(0).getIcon() != null && !listWetherList.getWeather().get(0).getIcon().isEmpty()) {
            Picasso.with(activity).load(icon).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(weather_icon);
            //weather_icon.setColorFilter(ContextCompat.getColor(activity, R.color.weather_icon_color));
        }
        //Weather
        double fhigh = Double.parseDouble(listWetherList.getMain().getTemp());
        span1 = new SpannableString(String.valueOf(Math.round(fhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize4), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        currenttemp.setText(finalText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }
}
