package com.donGumen.nickolas.ownweather;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Implementation of App Widget functionality.
 */
public class OwnWidget extends AppWidgetProvider {

    private CurrentWeatherModel currentWeather = new CurrentWeatherModel();
    private static RemoteViews v;
    AppWidgetManager a;
    int id;
    private static Context c;

//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.own_widget);
//        v = views;
//        c = context;
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
        v = new RemoteViews(context.getPackageName(), R.layout.own_widget);
        c = context;
        Intent intent =  new Intent(context, MainActivity.class);
        v.setOnClickPendingIntent(R.id.weatherImage, PendingIntent.getActivity(context,1,intent, 0));
        a = appWidgetManager;
        id = appWidgetIds[0];
        appWidgetManager.updateAppWidget(appWidgetIds[0], v);
        new WeatherDownloadTask().execute(MainActivity.name);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void setCurrentWeather(){
        String dd = "d" + currentWeather.id.substring(0, 2) + "d";
        String ss = "s" + currentWeather.id.substring(0, 2) + "d";
        String state = c.getString(Utils.getResId(ss, R.string.class));
        Calendar c = Calendar.getInstance();
        String hour = Integer.toString(c.get(Calendar.HOUR));
        String min = Integer.toString(c.get(Calendar.MINUTE));
        v.setTextViewText(R.id.weatherState, state);
        String temp = Double.toString(currentWeather.temp)  + "°";
        v.setImageViewResource(R.id.weatherImage,Utils.getResId(dd, R.drawable.class));
        v.setTextViewText(R.id.temperature, temp);
        a.updateAppWidget(id, v);
    }

    class WeatherDownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Network.buildURL(name, true))
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WeatherJson.setCurrentWeather(currentWeather, obj);
                setCurrentWeather();
//                model = WeatherJson.toWeatherModel(obj);
//                setAdapter(model);
            }
        }
    }
}

