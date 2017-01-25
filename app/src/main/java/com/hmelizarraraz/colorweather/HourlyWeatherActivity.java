package com.hmelizarraraz.colorweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.hmelizarraraz.colorweather.Adapters.HourlyWeatherAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyWeatherActivity extends Activity {

    @BindView(R.id.hourlyListView)
    ListView hourlyListView;

    @BindView(R.id.tvHourlyNoData)
    TextView tvHourlyNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        ArrayList<Hour> hours = intent.getParcelableArrayListExtra(MainActivity.HOURS_ARRAY_LIST);

        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(this, hours);

        hourlyListView.setAdapter(hourlyWeatherAdapter);

        hourlyListView.setEmptyView(tvHourlyNoData);
    }
}
