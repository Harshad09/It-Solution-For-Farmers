package com.example.olx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class Sollution extends AppCompatActivity {

    TextView t;
    ImageView image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Solutions </font>"));

        setContentView(R.layout.activity_sollution);

        t = findViewById(R.id.hashad);
        String sollution = getIntent().getStringExtra("diseaseLable");
        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        image = findViewById(R.id.imageSol);
        image.setImageBitmap(bitmap);
        t.setText(sollution);
    }
}
