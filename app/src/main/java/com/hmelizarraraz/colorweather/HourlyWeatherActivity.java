package com.hmelizarraraz.colorweather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.hmelizarraraz.colorweather.Adapters.HourlyWeatherAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyWeatherActivity extends Activity {

    @BindView(R.id.hourlyListView)
    ListView hourlyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);
        ButterKnife.bind(this);

        ArrayList<Hour> hours = new ArrayList<>();

        for (int i = 0; i<100; i++) {
            Hour hour = new Hour();

            hour.setHour("12:00");
            hour.setWeatherDescription("Cloudy position: " + i);

            hours.add(hour);
        }

        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(this, hours);

        hourlyListView.setAdapter(hourlyWeatherAdapter);
    }
}
