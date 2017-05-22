package com.example.nickolas.ownweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {


    ImageView weatherStateImage;
    TextView    weatherState, weatherDate, weatherDay, minTemp, maxTemp, eveTemp, dayTemp, mornTemp,nightTemp, humidity, pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();

        weatherStateImage = (ImageView) findViewById(R.id.weatherStateImage);
        weatherState = (TextView) findViewById(R.id.weatherState);
        weatherDate = (TextView) findViewById(R.id.weatherDate);
        weatherDay = (TextView) findViewById(R.id.weatherDay);

        minTemp = (TextView) findViewById(R.id.min_temperature);
        maxTemp = (TextView) findViewById(R.id.max_temperature);
        eveTemp = (TextView) findViewById(R.id.evening_temperature);
        dayTemp = (TextView) findViewById(R.id.day_temperature);
        mornTemp = (TextView) findViewById(R.id.morn_temperature);
        nightTemp = (TextView) findViewById(R.id.night_temperature);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setWeatherState(intent.getStringExtra("state"));
        setDate(intent.getLongExtra("date", 0));
        setTemp(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    void setDate(long mil) {
        weatherDay.setText(Utils.getWeekDayName(this, mil));
        weatherDate.setText(Utils.getDate(mil));
    }

    void setTemp(Intent i){
        dayTemp.append(" " + Double.toString(i.getDoubleExtra("dTemp", 0)) + "°");
        minTemp.append(" " + Double.toString(i.getDoubleExtra("minTemp", 0)) + "°");
        maxTemp.append(" " + Double.toString(i.getDoubleExtra("maxTemp", 0)) + "°");
        nightTemp.append(" " + Double.toString(i.getDoubleExtra("nTemp", 0)) + "°");
        eveTemp.append(" " + Double.toString(i.getDoubleExtra("eTemp", 0)) + "°");
        mornTemp.append(" " + Double.toString(i.getDoubleExtra("mTemp", 0)) + "°");
        humidity.append(" " + Integer.toString(i.getIntExtra("hum", 0)));
        Double d = i.getDoubleExtra("pres", 0);
        int pr = d.intValue();
        pressure.append(" " + Integer.toString(pr));

    }

    void setWeatherState(String s) {
        switch (s) {
            case "Clear":
                weatherStateImage.setImageResource(R.drawable.sunny);
                weatherState.setText(getString(R.string.Clear));
                break;
            case "Rain":
                weatherStateImage.setImageResource(R.drawable.rain);
                weatherState.setText(getString(R.string.rain));
                break;
            case "Clouds":
                weatherStateImage.setImageResource(R.drawable.mostly_cloudy);
                weatherState.setText(getString(R.string.clouds));
                break;
        }
    }


}
