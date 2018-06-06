package com.example.android.weather_mvp.presenter;

import com.example.android.weather_mvp.MVP_VP;
import com.example.android.weather_mvp.model.RetrofitClass;


public class WeatherPresenter implements MVP_VP.mvp_presentor,MVP_PM.presenter {

    private MVP_VP.mvp_view iView;

    public WeatherPresenter(MVP_VP.mvp_view iView){
        this.iView = iView;
    }

    public void updateView() {
        RetrofitClass retrofitClass = new RetrofitClass();
        retrofitClass.getWeatherModel(iView.getArea(),this);
    }

    public void updateViews(MVP_PM.model iModel) {

        if (iModel != null) {
            iView.updateTemperature(String.valueOf(Math.round(iModel.getMain().getTemp())));
            iView.updateMin(String.valueOf(Math.round(iModel.getMain().getTempMin())));
            iView.updateMax(String.valueOf(Math.round(iModel.getMain().getTempMax())));
            iView.updateHumidity(String.valueOf(Math.round(iModel.getMain().getHumidity())));
            iView.updateWind(String.valueOf(Math.round(iModel.getWind().getSpeed())));
            iView.updateWeather(iModel.getWeather().get(0).getDescription());
        } else {
            iView.updateErrorCard();
            iView.updateTemperature("");
            iView.updateMin("");
            iView.updateMax("");
            iView.updateHumidity("");
            iView.updateWind("");
            iView.updateWeather("");
        }
    }




}
