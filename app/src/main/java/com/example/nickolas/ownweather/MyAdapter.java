package com.example.nickolas.ownweather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nickolas on 22.05.2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    WeatherModel wm;
    Context context;

    public MyAdapter(WeatherModel wm, Context context) {
        this.wm = wm;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_weather_view, parent, false);

        ViewHolder vh = new ViewHolder(v);

        vh.day = (TextView) v.findViewById(R.id.weatherDay);
        vh.icon = (ImageView) v.findViewById(R.id.weatherImage);
        vh.state = (TextView) v.findViewById(R.id.weatherState);
        vh.max = (TextView) v.findViewById(R.id.weatherMaxTemp);
        vh.min  = (TextView) v.findViewById(R.id.weatherMinTemp);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , WeatherActivity.class);
                intent.pu("wm", wm);
                context.startActivity(intent);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.day.setText(Utils.getWeekDayName(context, wm.list.get(position).dt));
        holder.max.setText(Double.toString(wm.list.get(position).temp.max) + "°");
        holder.min.setText(Double.toString(wm.list.get(position).temp.min) + "°");
        holder.state.setText(wm.list.get(position).weather.main);
        switch (wm.list.get(position).weather.main){
            case "Clear":
                holder.icon.setImageResource(R.drawable.sunny);
                holder.state.setText(context.getString(R.string.Clear));
                break;
            case "Rain":
                holder.icon.setImageResource(R.drawable.rain);
                holder.state.setText(context.getString(R.string.rain));
                break;
            case "Clouds":
                holder.icon.setImageResource(R.drawable.cloud_with_sun);
                holder.state.setText(context.getString(R.string.clouds));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return wm.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, state, max, min;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
