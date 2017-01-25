package com.hmelizarraraz.colorweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    public static final String DATA = "data";
    public static final String SUMMARY = "summary";
    public static final String ICON = "icon";
    public static final String TEMPERATURE = "temperature";
    public static final String TEMPERATURE_MAX = "temperatureMax";
    public static final String TEMPERATURE_MIN = "temperatureMin";
    public static final String PRECIP_PROBABILITY = "precipProbability";
    public static final String TIME = "time";
    public static final String CURRENTLY = "currently";
    public static final String DAILY = "daily";
    public static final String HOURLY = "hourly";
    public static final String TIMEZONE = "timezone";
    public static final String DAYS_ARRAY_LIST = "days";
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String HOURS_ARRAY_LIST = "hours";

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

    ArrayList<Day> days;
    ArrayList<Hour> hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.darksky.net/forecast/f679dac969c1ac28556f21f62fd80b9f/37.8267,-122.4233?units=si&lang=es";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            CurrentWeather currentWeather = getCurrentWeatherFromJSON(response);
                            days = getDailyWeatherFromJSON(response);
                            hours = getHourlyWeatherFromJSON(response);
                            ArrayList<Minute> minutes = getMinutelyWeatherFromJSON(response);

                            imageIcon.setImageDrawable(currentWeather.getIconDrawableResource());
                            tvDescriptionText.setText(currentWeather.getDescription());
                            tvCurrentTemp.setText(currentWeather.getCurrentTemperature());
                            tvHighestTemp.setText(String.format("H: %sº",currentWeather.getHighestTemperature()));
                            tvLowestTemp.setText(String.format("L: %sº",currentWeather.getLowestTemperature()));
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

        dailyActivityIntent.putParcelableArrayListExtra(DAYS_ARRAY_LIST, days);

        startActivity(dailyActivityIntent);
    }

    @OnClick(R.id.tvHourly)
    public void hourlyWeatherClick() {
        Intent hourlyActivityIntent = new Intent(MainActivity.this, HourlyWeatherActivity.class);

        hourlyActivityIntent.putParcelableArrayListExtra(HOURS_ARRAY_LIST, hours);

        startActivity(hourlyActivityIntent);
    }

    @OnClick(R.id.tvMinutely)
    public void minutelyWeatherClik() {
        Intent minutelyActivityIntent = new Intent(MainActivity.this, MinutelyWeatherActivity.class);

        startActivity(minutelyActivityIntent);
    }

    private CurrentWeather getCurrentWeatherFromJSON(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);


        JSONObject jsonWithCurrentWeather = jsonObject.getJSONObject(CURRENTLY);

        String summary = jsonWithCurrentWeather.getString(SUMMARY);
        String icon = jsonWithCurrentWeather.getString(ICON);
        String temperature = Math.round(jsonWithCurrentWeather.getDouble(TEMPERATURE)) + "";

        JSONObject jsonWithDailyWeather = jsonObject.getJSONObject(DAILY);
        JSONArray jsonWithDailyWeatherData = jsonWithDailyWeather.getJSONArray(DATA);
        JSONObject jsonWithTodayData = jsonWithDailyWeatherData.getJSONObject(0);

        String maxTemp = Math.round(jsonWithTodayData.getDouble(TEMPERATURE_MAX)) + "";
        String minTemp = Math.round(jsonWithTodayData.getDouble(TEMPERATURE_MIN)) + "";

        CurrentWeather currentWeather = new CurrentWeather(MainActivity.this);
        currentWeather.setDescription(summary);
        currentWeather.setIconImage(icon);
        currentWeather.setCurrentTemperature(temperature);
        currentWeather.setHighestTemperature(maxTemp);
        currentWeather.setLowestTemperature(minTemp);

        return currentWeather;
    }

    private ArrayList<Day> getDailyWeatherFromJSON(String json) throws JSONException {

        DateFormat dateFormat = new SimpleDateFormat("EEEE");

        ArrayList<Day> days = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        String timeZone = jsonObject.getString(TIMEZONE);

        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

        JSONObject jsonWithDailyWeather = jsonObject.getJSONObject(DAILY);
        JSONArray jsonWithDailyWeatherData = jsonWithDailyWeather.getJSONArray(DATA);

        for (int i= 0; i<jsonWithDailyWeatherData.length(); i++) {
            Day day = new Day();

            JSONObject jsonWithDayData = jsonWithDailyWeatherData.getJSONObject(i);

            String rainProbability = "Probabilidad de lluvia: " + jsonWithDayData.getDouble(PRECIP_PROBABILITY)*100 + "%";
            String description = jsonWithDayData.getString(SUMMARY);
            String dayName = dateFormat.format(jsonWithDayData.getDouble(TIME)*1000);

            day.setDayName(dayName);
            day.setRainProbability(rainProbability);
            day.setWeatherDescription(description);

            days.add(day);
        }

        return days;
    }

    private ArrayList<Hour> getHourlyWeatherFromJSON(String response) throws JSONException {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        ArrayList<Hour> hours = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);

        String timeZone = jsonObject.getString(TIMEZONE);

        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

        JSONObject jsonWithHourlyWeather = jsonObject.getJSONObject(HOURLY);
        JSONArray jsonWithHourlyWeatherData = jsonWithHourlyWeather.getJSONArray(DATA);

        for (int i = 0; i<jsonWithHourlyWeatherData.length(); i++) {
            Hour hour = new Hour();

            JSONObject jsonWithHourData = jsonWithHourlyWeatherData.getJSONObject(i);

            String description = jsonWithHourData.getString(SUMMARY);
            String hourTitle = dateFormat.format(jsonWithHourData.getDouble(TIME)*1000);

            hour.setHour(hourTitle);
            hour.setWeatherDescription(description);

            hours.add(hour);
        }


        return hours;
    }

    private ArrayList<Minute> getMinutelyWeatherFromJSON(String response) throws JSONException {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        ArrayList<Minute> minutes = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);

        String timeZone = jsonObject.getString(TIMEZONE);

        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

        JSONObject jsonWithMinutelyWeather = jsonObject.getJSONObject("minutely");
        JSONArray jsonWithMinutelyWeatherData = jsonWithMinutelyWeather.getJSONArray(DATA);

        for (int i = 0; i<jsonWithMinutelyWeatherData.length(); i++) {
            Minute minute = new Minute();

            JSONObject jsonWithMinuteData = jsonWithMinutelyWeatherData.getJSONObject(i);

            String minuteTitle = dateFormat.format(jsonWithMinuteData.getDouble(TIME)*1000);
            String rainProbability = jsonWithMinuteData.getDouble(PRECIP_PROBABILITY) + "";

            minute.setTitle(minuteTitle);
            minute.setRainProbability(rainProbability);

            minutes.add(minute);

        }

        return minutes;
    }
}
