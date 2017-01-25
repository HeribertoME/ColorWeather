package com.hmelizarraraz.colorweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hmelizarraraz.colorweather.Hour;
import com.hmelizarraraz.colorweather.R;

import java.util.ArrayList;

/**
 * Created by heriberto on 24/01/17.
 */

public class HourlyWeatherAdapter extends BaseAdapter {

    ArrayList<Hour> hours;
    Context context;

    public HourlyWeatherAdapter(Context context, ArrayList<Hour> hours) {
        this.context = context;
        this.hours = hours;
    }


    @Override
    public int getCount() {
        return hours.size();
    }

    @Override
    public Object getItem(int position) {
        return hours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        HourlyViewHolder hourlyViewHolder;
        Hour hour = hours.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.hourly_list_item,viewGroup, false);
            hourlyViewHolder = new HourlyViewHolder();

            hourlyViewHolder.tvHourlyListHour = (TextView) view.findViewById(R.id.tvHourlyListHour);
            hourlyViewHolder.tvHourlyListDescription = (TextView) view.findViewById(R.id.tvHourlyListDescription);

            view.setTag(hourlyViewHolder);
        }else {
            hourlyViewHolder = (HourlyViewHolder) view.getTag();
        }

        hourlyViewHolder.tvHourlyListHour.setText(hour.getHour());
        hourlyViewHolder.tvHourlyListDescription.setText(hour.getWeatherDescription());

        return view;
    }

    static class HourlyViewHolder {
        TextView tvHourlyListHour;
        TextView tvHourlyListDescription;
    }
}
