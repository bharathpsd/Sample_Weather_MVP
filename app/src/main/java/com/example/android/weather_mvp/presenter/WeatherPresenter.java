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

    public void updateViews() {
        iView.updateErrorCard();
    }



}
