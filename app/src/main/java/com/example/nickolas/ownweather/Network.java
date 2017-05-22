package com.example.nickolas.ownweather;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Nickolas on 21.05.2017.
 */

public class Network {


    final static String QUERY_PARAM = "q";
    final static String BASE = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    final static String APID = "appid";
    final static String UNITS = "units";
    final static String CNT = "cnt";





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
