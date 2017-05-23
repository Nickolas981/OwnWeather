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
        vh.min = (TextView) v.findViewById(R.id.weatherMinTemp);


        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
        holder.day.setText(Utils.getWeekDayName(context, wm.list.get(position).dt));
        holder.max.setText(Double.toString(wm.list.get(position).temp.max) + "°");
        holder.min.setText(Double.toString(wm.list.get(position).temp.min) + "°");

        final Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra("date", wm.list.get(position).dt);
        intent.putExtra("dTemp", wm.list.get(position).temp.day);
        intent.putExtra("eTemp", wm.list.get(position).temp.eve);
        intent.putExtra("maxTemp", wm.list.get(position).temp.max);
        intent.putExtra("minTemp", wm.list.get(position).temp.min);
        intent.putExtra("nTemp", wm.list.get(position).temp.night);
        intent.putExtra("mTemp", wm.list.get(position).temp.morn);
        intent.putExtra("hum", wm.list.get(position).humidity);
        intent.putExtra("pres", wm.list.get(position).pressure * 0.75);




        holder.icon.setImageResource(Utils.getResId("d" + wm.list.get(position).weather.icon, R.drawable.class));
        intent.putExtra("photoId", Utils.getResId("d" + wm.list.get(position).weather.icon, R.drawable.class));
        holder.state.setText(context.getString(Utils.getResId("s" + wm.list.get(position).weather.icon, R.string.class)));
//        holder.state.setText(wm.list.get(position).weather.decription);
//        switch (wm.list.get(position).weather.decription){
//            case "sky is clear":
//                holder.icon.setImageResource(R.drawable.sunny);
//                intent.putExtra("photoId", R.drawable.sunny);
//                holder.state.setText(context.getString(R.string.s01d));
//                break;
//            case "light rain":
//                holder.icon.setImageResource(R.drawable.light_rain);
//                intent.putExtra("photoId", R.drawable.light_rain);
//                holder.state.setText(context.getString(R.string.light_rain));
//                break;
//            case "moderate rain":
//                holder.icon.setImageResource(R.drawable.rain);
//                intent.putExtra("photoId", R.drawable.rain);
//                holder.state.setText(context.getString(R.string.moderate_rain));
//                break;
//            case "scattered clouds":
//                holder.icon.setImageResource(R.drawable.mostly_cloudy);
//                intent.putExtra("photoId", R.drawable.mostly_cloudy);
//                holder.state.setText(context.getString(R.string.s03d));
//
//
//                break;
//        }
        intent.putExtra("state", holder.state.getText().toString());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wm.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, state, max, min;
        ImageView icon;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }
    }
}
