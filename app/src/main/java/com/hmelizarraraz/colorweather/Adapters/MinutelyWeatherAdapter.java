package com.hmelizarraraz.colorweather.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmelizarraraz.colorweather.Minute;
import com.hmelizarraraz.colorweather.R;

import java.util.ArrayList;

/**
 * Created by heriberto on 24/01/17.
 */

public class MinutelyWeatherAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Minute> minutes;

    public MinutelyWeatherAdapter(Context context, ArrayList<Minute> minutes){
        this.context = context;
        this.minutes = minutes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.minutely_list_item, parent, false);

        MinutelyViewHolder minutelyViewHolder = new MinutelyViewHolder(view);
        return minutelyViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MinutelyViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {

        if (minutes == null)
            return 0;
        return minutes.size();
    }

    class MinutelyViewHolder extends RecyclerView.ViewHolder {

        TextView tvMinutelyListTitle;
        TextView tvMinutelyListDescription;

        public MinutelyViewHolder(View itemView) {
            super(itemView);

            tvMinutelyListTitle = (TextView) itemView.findViewById(R.id.tvMinutelyListTitle);
            tvMinutelyListDescription = (TextView) itemView.findViewById(R.id.tvMinutelyListDescription);
        }

        public void onBind(int position) {
            Minute minute = minutes.get(position);

            tvMinutelyListTitle.setText(minute.getTitle());
            tvMinutelyListDescription.setText(minute.getRainProbability());
        }
    }
}
