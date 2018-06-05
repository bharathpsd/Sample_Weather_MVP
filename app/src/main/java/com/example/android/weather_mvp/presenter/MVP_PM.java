package com.example.android.weather_mvp.presenter;

import com.example.android.weather_mvp.model.Main;
import com.example.android.weather_mvp.model.Weather;
import com.example.android.weather_mvp.model.Wind;

import java.util.List;

public interface MVP_PM {

    interface presenter{}
    interface model{
        List<Weather> getWeather();
        Main getMain();
        Wind getWind();

    }
}
