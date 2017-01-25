package com.hmelizarraraz.colorweather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by heriberto on 24/01/17.
 */

public class Hour implements Parcelable{

    private String hour;
    private String weatherDescription;

    public Hour(){}

    protected Hour(Parcel in) {
        hour = in.readString();
        weatherDescription = in.readString();
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel in) {
            return new Hour(in);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    @Override
    public int describeContents() {
        return 0; // No se usará
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hour);
        dest.writeString(weatherDescription);
    }
}
