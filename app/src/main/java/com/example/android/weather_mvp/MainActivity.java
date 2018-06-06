package com.example.android.weather_mvp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.android.weather_mvp.model.WeatherModel;
import com.example.android.weather_mvp.presenter.WeatherPresenter;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity implements MVP_VP.mvp_view {
    protected GeoDataClient mGeoDataClient;
    AutoCompleteTextView autoCompleteTextView;
    PlaceAutoCompleteAdapter placeAutoCompleteAdapter;
    CardView cardView;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136)
    );

    WeatherPresenter presenter;
    AutocompleteFilter filter;
    String area;
    TextView place, temp, min, max, humidity, wind, weather;

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new WeatherPresenter(this);

        //Initialize views

        place = findViewById(R.id.place);
        temp = findViewById(R.id.temp);
        min = findViewById(R.id.min_temp);
        max = findViewById(R.id.max_temp);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind_speed);
        weather = findViewById(R.id.weather_desc);
        cardView = findViewById(R.id.error_card);


        cardView.setVisibility(View.INVISIBLE);
        autoCompleteTextView = findViewById(R.id.autocomplete);
        filter = new AutocompleteFilter.Builder().setCountry("US").build();
        mGeoDataClient = Places.getGeoDataClient(this);
        placeAutoCompleteAdapter = new PlaceAutoCompleteAdapter(this,mGeoDataClient,LAT_LNG_BOUNDS,filter);
        autoCompleteTextView.setAdapter(placeAutoCompleteAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {

                if(cardView.getVisibility() == View.VISIBLE){
                    cardView.setVisibility(View.INVISIBLE);
                }

                setArea(autoCompleteTextView.getText().toString());
                autoCompleteTextView.setText("");
                    presenter.updateView();
                InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(
                        autoCompleteTextView.getWindowToken(), 0);
            }
        });
    }

    public void setArea(String area) {
        this.area = area;
        updatePlace();
    }

    public String getArea() {
        return area;
    }

    public void updatePlace() {
        this.place.setText(getArea());
    }

    @Override
    public void updateTemperature(String temp) {
        this.temp.setText(temp);
    }

    @Override
    public void updateMin(String min) {
        this.min.setText(min);
    }

    @Override
    public void updateMax(String max) {
        this.max.setText(max);
    }

    @Override
    public void updateHumidity(String hum) {
        this.humidity.setText(hum);
    }

    @Override
    public void updateWind(String wind) {
        this.wind.setText(wind);
    }

    @Override
    public void updateWeather(String weather) {
        this.weather.setText(weather);
    }

    @Override
    public  void updateErrorCard(){
        this.cardView.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void onEvent(WeatherModel iModel){
            updateTemperature(String.valueOf(Math.round(iModel.getMain().getTemp())));
            updateMin(String.valueOf(Math.round(iModel.getMain().getTempMin())));
            updateMax(String.valueOf(Math.round(iModel.getMain().getTempMax())));
            updateHumidity(String.valueOf(Math.round(iModel.getMain().getHumidity())));
            updateWind(String.valueOf(Math.round(iModel.getWind().getSpeed())));
            updateWeather(iModel.getWeather().get(0).getDescription());

    }

}
