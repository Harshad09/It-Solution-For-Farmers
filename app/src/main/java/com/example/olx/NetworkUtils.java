package com.example.olx;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class NetworkUtils {

    private static final String TAG = "NetworkUtils";

    private final static String WEATHERDB_BASE_URL=
            "https://api.openweathermap.org/data/2.5/forecast";

    private final static String API_KEY = "c5280e4898b0e40240f49c10ddcbbd3f";

   // private static final String TAG = "NetworkUtils";

    private final static String PARAMETER_URL="https://sih-2020-1.herokuapp.com/";

    public static URL buildUrlForParameter() {
        URL url =null;


        try {
            url = new URL((PARAMETER_URL).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "buildUrlForParameter: url: "+url);
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in  = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static URL buildUrlForWeather(String latitude,String longitude) {
        URL url =null;


        try {
            url = new URL((WEATHERDB_BASE_URL + "?appid=" + API_KEY +"&lat="+latitude+"&lon="+longitude).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "buildUrlForWeather: url: "+url);
        return url;
    }

    public static String getResponseFromHttpsUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in  = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
