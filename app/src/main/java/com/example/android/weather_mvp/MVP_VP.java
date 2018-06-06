package com.example.android.weather_mvp;

import java.io.IOException;

public interface MVP_VP {

    interface mvp_view{
         String getArea();
         void updateTemperature(String temp);
         void updateMin(String min);
         void updateMax(String max);
         void updateHumidity(String hum);
         void updateWind(String wind);
         void updateWeather(String weather);
         void updateErrorCard();
    }
    interface mvp_presentor{
        void updateView() throws IOException;
    }
}
