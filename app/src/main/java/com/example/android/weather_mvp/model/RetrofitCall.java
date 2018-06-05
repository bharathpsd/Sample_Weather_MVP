package com.example.android.weather_mvp.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitCall {
    String BASE_URL = "http://api.openweathermap.org/";

    @GET("data/2.5/weather")
    Call<WeatherModel> getDetails(@Query("q") String q,@Query("units") String units,@Query("appid") String appID);}
