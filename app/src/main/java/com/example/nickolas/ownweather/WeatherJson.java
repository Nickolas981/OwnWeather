package com.example.nickolas.ownweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nickolas on 22.05.2017.
 */

public class WeatherJson {

    public static WeatherModel toWeatherModel(JSONObject object) {
        WeatherModel wm = new WeatherModel();

        setCity(wm, object);
        try {
            wm.cod = object.getString("cod");
            wm.message = object.getInt("message");
            wm.cnt = object.getInt("cnt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setList(wm, object);

        return wm;
    }

    private static void setList(WeatherModel wm, JSONObject object) {

        try {
            JSONArray w = object.getJSONArray("list");
            for (int i = 0; i < w.length(); i++) {
                JSONObject obj = w.getJSONObject(i);
                Weather weather = new Weather();
                weather.dt = obj.getLong("dt");
                setTemp(weather, obj);
                weather.presure = obj.getDouble("pressure");
                weather.humidity = obj.getInt("humidity");
                setWeather(weather, obj);
                weather.speed = obj.getDouble("speed");
                weather.deg = obj.getInt("deg");
                weather.clouds = obj.getInt("clouds");
                wm.list.add(weather);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void setWeather(Weather w, JSONObject obj) {
        try {
            JSONArray weather = obj.getJSONArray("weather");
            JSONObject weath = weather.getJSONObject(0);
            w.weather.id = weath.getInt("id");
            w.weather.main = weath.getString("main");
            w.weather.decription = weath.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void setTemp(Weather w, JSONObject obj) {
        try {
            JSONObject temp = obj.getJSONObject("temp");
            w.temp.day = temp.getDouble("day");
            w.temp.min = temp.getDouble("min");
            w.temp.max = temp.getDouble("max");
            w.temp.night = temp.getDouble("night");
            w.temp.eve = temp.getDouble("eve");
            w.temp.morn = temp.getDouble("morn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private static void setCity(WeatherModel wm, JSONObject object) {

        try {
            JSONObject city = object.getJSONObject("city");
            wm.city.id = city.getInt("id");
            wm.city.name = city.getString("name");
            wm.city.country = city.getString("country");
            wm.city.population = city.getInt("population");
            setCoordinats(wm, city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void setCoordinats(WeatherModel wm, JSONObject city) {
        try {
            JSONObject coord = city.getJSONObject("coord");
            wm.city.coordinates.lon = coord.getDouble("lon");
            wm.city.coordinates.lat = coord.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
