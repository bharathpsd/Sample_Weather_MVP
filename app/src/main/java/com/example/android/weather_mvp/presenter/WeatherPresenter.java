package com.example.android.weather_mvp.presenter;

import com.example.android.weather_mvp.MVP_VP;
import com.example.android.weather_mvp.model.RetrofitClass;
import com.example.android.weather_mvp.presenter.MVP_PM;

import java.io.IOException;


public class WeatherPresenter implements MVP_VP.mvp_presentor,MVP_PM.presenter {

    private MVP_VP.mvp_view iView;
    private MVP_PM.model iModel;
    private RetrofitClass retrofitClass;

    public WeatherPresenter(MVP_VP.mvp_view iView){
        this.iView = iView;
    }
    public WeatherPresenter(){};

    public void updateView() {
        retrofitClass = new RetrofitClass();
        retrofitClass.getWeatherModel(iView.getArea(),this);
    }

    public void updateViews(MVP_PM.model iModel){
        iView.updateTemperature(iModel.getMain().getTemp().toString());
        iView.updateMin(iModel.getMain().getTempMin().toString());
        iView.updateMax(iModel.getMain().getTempMax().toString());
        iView.updateHumidity(iModel.getMain().getHumidity().toString());
        iView.updateWind(iModel.getWind().getSpeed().toString());
        iView.updateWeather(iModel.getWeather().get(0).getDescription());
    }


}
