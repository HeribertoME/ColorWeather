package com.hmelizarraraz.colorweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.imageIcon)
    ImageView imageIcon;

    @BindView(R.id.tvDescriptionText)
    TextView tvDescriptionText;

    @BindView(R.id.tvCurrentTemp)
    TextView tvCurrentTemp;

    @BindView(R.id.tvHighestTemp)
    TextView tvHighestTemp;

    @BindView(R.id.tvLowestTemp)
    TextView tvLowestTemp;

    @BindDrawable(R.drawable.sunny)
    Drawable sunny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        CurrentWeather currentWeather = new CurrentWeather(MainActivity.this);
        currentWeather.setIconImage("clear-night");
        currentWeather.setDescription("Sunny day");
        currentWeather.setCurrentTemperature("19");
        currentWeather.setHighestTemperature("H:25º");
        currentWeather.setLowestTemperature("L:10º");

        imageIcon.setImageDrawable(currentWeather.getIconDrawableResource());
        tvDescriptionText.setText(currentWeather.getDescription());
        tvCurrentTemp.setText(currentWeather.getCurrentTemperature());
        tvHighestTemp.setText(currentWeather.getHighestTemperature());
        tvLowestTemp.setText(currentWeather.getLowestTemperature());

    }

    @OnClick(R.id.tvDaily)
    public void dailyWeatherClick () {
        Intent dailyActivityIntent= new Intent(MainActivity.this, DailyWeatherActivity.class);

        startActivity(dailyActivityIntent);
    }

    @OnClick(R.id.tvHourly)
    public void hourlyWeatherClick() {
        Intent hourlyActivityIntent = new Intent(MainActivity.this, HourlyWeatherActivity.class);

        startActivity(hourlyActivityIntent);
    }

    @OnClick(R.id.tvMinutely)
    public void minutelyWeatherClik() {
        Intent minutelyActivityIntent = new Intent(MainActivity.this, MinutelyWeatherActivity.class);

        startActivity(minutelyActivityIntent);
    }
}
