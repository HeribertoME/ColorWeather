package com.hmelizarraraz.colorweather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by heriberto on 24/01/17.
 */

public class Minute implements Parcelable{

    private String title;
    private String rainProbability;

    public Minute(){}

    protected Minute(Parcel in) {
        title = in.readString();
        rainProbability = in.readString();
    }

    public static final Creator<Minute> CREATOR = new Creator<Minute>() {
        @Override
        public Minute createFromParcel(Parcel in) {
            return new Minute(in);
        }

        @Override
        public Minute[] newArray(int size) {
            return new Minute[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(String rainProbability) {
        this.rainProbability = rainProbability;
    }

    @Override
    public int describeContents() {
        return 0; // We don't use this method
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(rainProbability);
    }
}
