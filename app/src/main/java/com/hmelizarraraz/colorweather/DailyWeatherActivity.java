package com.hmelizarraraz.colorweather;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.hmelizarraraz.colorweather.Adapters.DailyWeatherAdapter;

import java.util.ArrayList;

public class DailyWeatherActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);

        ArrayList<Day> days  = new ArrayList<Day>();

        for (int i = 0; i < 500; i++) {
            Day day = new Day();

            day.setDayName("Monday");
            day.setWeatherDescription("Day's description");
            day.setRainProbability("Rain probability 0%");

            days.add(day);
        }


        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(this, days);

        setListAdapter(dailyWeatherAdapter);
    }
}
