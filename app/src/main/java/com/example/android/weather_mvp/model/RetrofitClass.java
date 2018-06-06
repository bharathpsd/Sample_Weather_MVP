package com.example.android.weather_mvp.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.weather_mvp.presenter.WeatherPresenter;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass {
    private WeatherModel weatherModel = null;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitCall.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getWeatherModel(String q, final WeatherPresenter presenter) {
        final EventBus bus = EventBus.getDefault();
        RetrofitCall api = retrofit.create(RetrofitCall.class);
        Call<WeatherModel> call = api.getDetails(q, "metric", "1c83394176ea8a8eda327198e397d54b");

        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                if(response.isSuccessful()) {
                    weatherModel = response.body();
                    bus.post(weatherModel);
                } else {
                    presenter.updateViews();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                Log.e("Error","------------>" + t.getMessage());
            }
        });
    }
}
