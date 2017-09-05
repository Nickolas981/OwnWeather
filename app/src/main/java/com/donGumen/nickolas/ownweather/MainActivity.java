package com.donGumen.nickolas.ownweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.r0adkll.slidr.Slidr;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private WeatherModel model;
    private Menu menu;
    public static Activity activity;
    public static String name;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                loadWeather(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(MainActivity.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }

        });
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        getPreference();
        if (name == null || name.equals("")){
            int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                .build(this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException ignored) {
            }
        }else{
            loadWeather(name);
            getSupportActionBar().setTitle("OwnWeather (" + name + ")");
        }
        Location location = new GPSTracker(this).getLocation();
        if (location != null){
            Toast.makeText(this, "lat " + location.getLatitude() + "\nlon " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                name = place.getName().toString();
                loadWeather(name);
                getSupportActionBar().setTitle("OwnWeather (" + name + ")");
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Toast.makeText(MainActivity.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {


            }
        }
    }

    @Override
    protected void onStop() {
        savePreference();
        super.onStop();
    }

    void savePreference(){
        sharedPreferences  = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("saved_text", name);
        ed.commit();
    }

    void getPreference(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        name = sharedPreferences.getString("saved_text", "");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException ignored) {
                }

//                MenuItem item1 = menu.findItem(R.id.action_geo);
//                item1.setVisible(true);
                break;
//            case R.id.action_geo:
//                String str = "geo:" + Double.toString(model.city.coordinates.lat)+ "," + Double.toString(model.city.coordinates.lon);
//                Uri.Builder builder = new Uri.Builder();
//                Uri uri = Uri.parse(str);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(uri);
//                startActivity(intent);
        }
        return false;
    }

    void loadWeather(String s) {
        new WeatherDownloadTask().execute(s);
    }

    void setAdapter(WeatherModel wm) {
        mRecyclerView.setAdapter(new MyAdapter(wm, this));
    }

    class WeatherDownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            mRecyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Network.buildURL(name))
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
            progressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (s != null && !s.equals("")) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                model = WeatherJson.toWeatherModel(obj);
                setAdapter(model);
            }
        }
    }
    class WeatherCDownloadTask extends AsyncTask<Void, Void, String> {
        double lat, lon;


        @Override
        protected void onPreExecute() {
            mRecyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            Location location  = new GPSTracker(MainActivity.activity).getLocation();
            if (location != null){
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            String latitude = Double.toString(lat);
            String longtitude = Double.toString(lon);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Network.buildURL(latitude, longtitude))
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
            progressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (s != null && !s.equals("")) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                model = WeatherJson.toWeatherModel(obj);
                setAdapter(model);
            }
        }
    }


}
