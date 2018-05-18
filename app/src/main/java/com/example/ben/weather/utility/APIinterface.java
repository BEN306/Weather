package com.example.ben.weather.utility;

import com.example.ben.weather.deo.Wether;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIinterface {
    @GET("http://api.openweathermap.org/data/2.5/forecast?APPID=714c5ec0dd555893ad012d5160bbce13&mode=jshon&cnt=16")
    Call<Wether>getWether(@Query("q") String city, @Query("units") String metric);

}