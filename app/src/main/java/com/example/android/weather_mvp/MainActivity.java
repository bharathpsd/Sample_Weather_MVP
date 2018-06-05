package com.example.android.weather_mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.weather_mvp.presenter.WeatherPresenter;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MainActivity extends AppCompatActivity implements MVP_VP.mvp_view{
    protected GeoDataClient mGeoDataClient;
    AutoCompleteTextView autoCompleteTextView;
    PlaceAutoCompleteAdapter placeAutoCompleteAdapter;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168),new LatLng(71,136)
    ) ;

    WeatherPresenter presenter;
    AutocompleteFilter filter;
    String area;
    TextView place,temp,min,max,humidity,wind,weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new WeatherPresenter(this);
        place = findViewById(R.id.place);
        temp = findViewById(R.id.temp);
        min = findViewById(R.id.min_temp);
        max = findViewById(R.id.max_temp);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind_speed);
        weather = findViewById(R.id.weather_desc);

        autoCompleteTextView = findViewById(R.id.autocomplete);
        filter = new AutocompleteFilter.Builder().setCountry("US").build();
        mGeoDataClient = Places.getGeoDataClient(this);
        placeAutoCompleteAdapter = new PlaceAutoCompleteAdapter(this,mGeoDataClient,LAT_LNG_BOUNDS,filter);
        autoCompleteTextView.setAdapter(placeAutoCompleteAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                Toast.makeText(MainActivity.this,autoCompleteTextView.getText(),Toast.LENGTH_SHORT).show();
                setArea(autoCompleteTextView.getText().toString());
                    presenter.updateView();

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
}
