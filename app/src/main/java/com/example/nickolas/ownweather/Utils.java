package com.example.nickolas.ownweather;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {

    public static String getWeekDayName(Context context, long mil) {
        long time = System.currentTimeMillis();
        long time1 = System.nanoTime();
        long oneDay = 86400000;

        Date tommorow = new Date(time + oneDay),
                today = new Date(time),
                date = new Date(mil * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String res = sdf.format(date);

        if (res.equals(sdf.format(today))) {
            res = context.getString(R.string.today);
        } else if (res.equals(sdf.format(tommorow))) {
            res = context.getString(R.string.tommorow);
        }
        res = Character.toUpperCase(res.charAt(0)) + res.substring(1);
        return res;
    }
}
