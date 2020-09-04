package com.example.olx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class Irrigation extends AppCompatActivity {

    RelativeLayout weforcast;
    private static final String TAG = Irrigation.class.getSimpleName();
    TextView temp;
    TextView tex1tView13;
    TextView textView13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_irrigation);

        weforcast = findViewById(R.id.weather_forcast);
        temp = findViewById(R.id.temp);
        tex1tView13 = findViewById(R.id.tex1tView13);
        textView13 = findViewById(R.id.textView13);
        URL parameterUrl = NetworkUtils.buildUrlForParameter();
        new FetchParameterDetails().execute(parameterUrl);
        Log.i(TAG, "onCreate: parameterUrl: " + parameterUrl);


        weforcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WeatherActivity.class));
            }
        });


    }
    private class FetchParameterDetails extends AsyncTask<URL, Void, String>
    {


        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(URL... urls) {
            URL parameterUrl = urls[0];
            String parameterSearchResults = null;
            try {
                parameterSearchResults = NetworkUtils.getResponseFromHttpUrl(parameterUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: parameterSearchResults: " + parameterSearchResults);
            return parameterSearchResults;
        }


        @Override
        protected void onPostExecute(String parameterSearchResults) {
            if (parameterSearchResults != null && !parameterSearchResults.equals("")) {
                //Just for testing
                try {
                    JSONObject rootObject = new JSONObject(parameterSearchResults);
                    JSONObject result = rootObject.getJSONObject("orange");
                    String pH = result.getString("pH").substring(0,6);
                    String moister = result.getString("moister").substring(0,6);
                    textView13.setText(pH);
                    tex1tView13.setText(moister);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(parameterSearchResults);
        }




    }
}