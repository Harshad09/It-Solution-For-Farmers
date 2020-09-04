package com.example.olx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

public class WeatherActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    TextView showLocation;
    TextView showPlace;
    TextView showDate;
    TextView showTemp;
    TextView showDesc;
    ListView listView;
    LocationManager locationManager;
    String latitude, longitude;
    private ArrayList<Weather> weatherArrayList = new ArrayList<>();
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        showLocation = findViewById(R.id.showLocation);
        showPlace = findViewById(R.id.showPlace);
        listView = findViewById(R.id.idListView);
        showTemp = findViewById(R.id.showTemp);
        showDate =findViewById(R.id.showDate);
        showDesc =findViewById(R.id.showDesc);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                WeatherActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                WeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                URL weatherUrl = NetworkUtils.buildUrlForWeather(latitude,longitude);
                new FetchWeatherDetails().execute(weatherUrl);
                Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);
                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude+"\n");
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {


        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;
            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpsUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: weatherSearchResults: " + weatherSearchResults);
            return weatherSearchResults;
        }

        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if (weatherSearchResults != null && !weatherSearchResults.equals("")) {
                weatherArrayList = parseJSON(weatherSearchResults);
                //Just for testing
                Iterator itr = weatherArrayList.iterator();
                while(itr.hasNext()) {
                    Weather weatherInIterator = (Weather) itr.next();
                    Log.i(TAG, "onPostExecute: Date: " + weatherInIterator.getDate()+
                            " Temp: " + weatherInIterator.getTemp() +
                            " Description: " + weatherInIterator.getDesc());
                }

            }
            super.onPostExecute(weatherSearchResults);
        }
    }
    private ArrayList<Weather> parseJSON(String weatherSearchResults) {
        if(weatherArrayList != null) {
            weatherArrayList.clear();
        }
        if(weatherSearchResults != null) {
            try {
                JSONObject rootObject = new JSONObject(weatherSearchResults);
                JSONArray results = rootObject.getJSONArray("list");
                String place = rootObject.getJSONObject("city").getString("name");
                showPlace.setText("Weather In Your Area "+place+" :");
                String pre = null;
                for (int i = 0; i < results.length(); i++) {
                    Weather weather = new Weather();
                    JSONObject resultsObj = results.getJSONObject(i);
                    String datetime = resultsObj.getString("dt_txt");
                    String date = datetime.substring(0,10);
                    if(!date.equals(pre))
                    {
                        weather.setDate(date);
                        Double mainobj =resultsObj.getJSONObject("main").getDouble("temp");
                        String temp = String.format("%.2f",mainobj-273.15)+"°";
                        weather.setTemp(temp);

                        JSONArray weatherobj =resultsObj.getJSONArray("weather");
                        JSONObject weath = weatherobj.getJSONObject(0);
                        int id = weath.getInt("id");
                        if(id==500)
                        {
                            String desc = weath.getString("description");
                            weather.setDesc(desc);
                        }
                        else
                        {
                            String desc = weath.getString("main");
                            weather.setDesc(desc);
                        }
                        weatherArrayList.add(weather);
                    }
                    pre = date;
                }
                if(weatherArrayList != null) {
                    WeatherAdapter weatherAdapter = new WeatherAdapter(this, weatherArrayList);
                    listView.setAdapter(weatherAdapter);

                }
                return weatherArrayList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public class WeatherAdapter extends ArrayAdapter<Weather> {

        public WeatherAdapter(@NonNull Context context, ArrayList<Weather> weatherArrayList) {
            super(context, 0, weatherArrayList);
        }
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Weather weather = getItem(position);

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView dateTextView = convertView.findViewById(R.id.showDate);
            TextView TempTextView = convertView.findViewById(R.id.showTemp);
            TextView DescTextView = convertView.findViewById(R.id.showDesc);

            dateTextView.setText(weather.getDate());
            TempTextView.setText(weather.getTemp());
            DescTextView.setText(weather.getDesc());
            return convertView;
        }
    }
}
