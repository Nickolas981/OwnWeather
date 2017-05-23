package com.example.nickolas.ownweather;

import java.util.ArrayList;

/**
 * Created by Nickolas on 22.05.2017.
 */

public class WeatherModel {

    City city = new City();
    int message, cnt;
    String cod;
    ArrayList<Weather> list = new ArrayList<>();

    class City {
        int id, population;
        String name, country;
        Coordinates coordinates = new Coordinates();

        class Coordinates {
            double lon, lat;
        }
    }
}
class Weather {
    long dt;
    Tempreture temp = new Tempreture();
    double pressure, speed;
    int humidity, deg, clouds;
    Sky weather = new Sky();

    class Sky {
        int id;
        String main, decription, icon;
    }

    class Tempreture {
        double day, min, max, night, eve, morn;
    }
}