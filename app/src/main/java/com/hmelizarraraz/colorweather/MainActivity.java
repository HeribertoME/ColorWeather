package com.hmelizarraraz.colorweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.darksky.net/forecast/f679dac969c1ac28556f21f62fd80b9f/19.645625,-98.984991";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            CurrentWeather currentWeather = getCurrentWeatherFromJSON(response);

                            imageIcon.setImageDrawable(currentWeather.getIconDrawableResource());
                            tvDescriptionText.setText(currentWeather.getDescription());
                            tvCurrentTemp.setText(currentWeather.getCurrentTemperature());
                            tvHighestTemp.setText(currentWeather.getHighestTemperature());
                            tvLowestTemp.setText(currentWeather.getLowestTemperature());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

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

    private CurrentWeather getCurrentWeatherFromJSON(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);


        JSONObject jsonWithCurrentWeather = jsonObject.getJSONObject("currently");

        String summary = jsonWithCurrentWeather.getString("summary");
        String icon = jsonWithCurrentWeather.getString("icon");
        String temperature = jsonWithCurrentWeather.getDouble("temperature") + "";

        JSONObject jsonWithDailyWeather = jsonObject.getJSONObject("daily");
        JSONArray jsonWithDailyWeatherData = jsonWithDailyWeather.getJSONArray("data");
        JSONObject jsonWithTodayData = jsonWithDailyWeatherData.getJSONObject(0);

        String maxTemp = jsonWithTodayData.getDouble("temperatureMax") + "";
        String minTemp = jsonWithTodayData.getDouble("temperatureMin") + "";

        CurrentWeather currentWeather = new CurrentWeather(MainActivity.this);
        currentWeather.setDescription(summary);
        currentWeather.setIconImage(icon);
        currentWeather.setCurrentTemperature(temperature);
        currentWeather.setHighestTemperature(maxTemp);
        currentWeather.setLowestTemperature(minTemp);

        return currentWeather;
    }
}
