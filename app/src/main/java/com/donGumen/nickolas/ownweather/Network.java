package com.donGumen.nickolas.ownweather;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nickolas on 21.05.2017.
 */

public class Network {


    final static String QUERY_PARAM = "q";
    final static String BASE = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    final static String CURRENT_BASE = "http://api.openweathermap.org/data/2.5/weather";
    final static String BASE_GEO = "api.openweathermap.org/data/2.5/forecast?";

    final static String APID = "appid";
    final static String LAT = "lat";
    final static String LON = "lon";
    final static String UNITS = "units";
    final static String CNT = "cnt";





    public static URL buildURL(String lat, String lon){


        Uri uri = Uri.parse(BASE).buildUpon()
                .appendQueryParameter(LAT, lat)
                .appendQueryParameter(LON, lon)
                .appendQueryParameter(APID, "a1597f52960fc0627bf6c27a9e23e0e3")
                .appendQueryParameter(UNITS, "metric")
                .appendQueryParameter(CNT, "7")
                .build();
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildURL(String name, boolean current){
        Uri uri = Uri.parse(CURRENT_BASE).buildUpon()
                .appendQueryParameter(QUERY_PARAM, name)
                .appendQueryParameter(APID, "a1597f52960fc0627bf6c27a9e23e0e3")
                .appendQueryParameter(UNITS, "metric")
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildURL(String s){
             Uri uri = Uri.parse(BASE).buildUpon()
                .appendQueryParameter(QUERY_PARAM, s)
                .appendQueryParameter(APID, "a1597f52960fc0627bf6c27a9e23e0e3")
                .appendQueryParameter(UNITS, "metric")
                .appendQueryParameter(CNT, "7")
                .build();


        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

//    public static String getResponseFromHttpUrl(URL url) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = urlConnection.getInputStream();
//
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                return scanner.next();
//            } else {
//                return null;
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//    }

}
