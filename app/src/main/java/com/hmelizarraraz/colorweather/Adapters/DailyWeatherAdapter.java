package com.hmelizarraraz.colorweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hmelizarraraz.colorweather.Day;
import com.hmelizarraraz.colorweather.R;

import java.util.ArrayList;

/**
 * Created by heriberto on 23/01/17.
 */

public class DailyWeatherAdapter extends BaseAdapter {

    ArrayList<Day> days;
    Context context;

    public DailyWeatherAdapter (Context context, ArrayList<Day> days) {
        this.context = context;
        this.days = days;
    }


    @Override
    public int getCount() {

        if (days == null)
            return 0;

        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        Day day = days.get(position);

        if (view == null) {

            view = LayoutInflater.from(context).inflate(R.layout.daily_list_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.dayTitle = (TextView) view.findViewById(R.id.tvDailyListTitle);
            viewHolder.dayDescription = (TextView) view.findViewById(R.id.tvDailyListDescription);
            viewHolder.dayRain = (TextView) view.findViewById(R.id.tvDailyListProbability);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.dayTitle.setText(day.getDayName());
        viewHolder.dayDescription.setText(day.getWeatherDescription());
        viewHolder.dayRain.setText(day.getRainProbability());

        return view;
    }

    static class ViewHolder{
        TextView dayTitle;
        TextView dayDescription;
        TextView dayRain;
    }
}
