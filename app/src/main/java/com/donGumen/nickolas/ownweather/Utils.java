package com.donGumen.nickolas.ownweather;

import android.content.Context;

import java.lang.reflect.Field;
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

    public static String getDate(long mil) {
        String res;

        SimpleDateFormat dfm = new SimpleDateFormat("dd MMM");

        Date date = new Date(mil * 1000);

        res = dfm.format(date);

        return res;
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
