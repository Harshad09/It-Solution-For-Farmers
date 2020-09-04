package com.example.olx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class Home extends AppCompatActivity
{

    ImageView myprofile;
    ImageView logout;
    RelativeLayout olx;
    RelativeLayout irrigation;
    RelativeLayout scheme;
    RelativeLayout disease;
    RelativeLayout chatbot;
    RelativeLayout forum;
    //CarouselView carouselView;
    //int[] sampleImages = {R.drawable.caro1, R.drawable.caro2, R.drawable.caro3, R.drawable.caro4, R.drawable.caro5};




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        //carouselView = findViewById(R.id.carouselView);
        //carouselView.setPageCount(sampleImages.length);
        //carouselView.setImageListener(imageListener);

        myprofile = findViewById(R.id.myprofile);
        logout = findViewById(R.id.logout);
        olx = findViewById(R.id.olx);
        irrigation = findViewById(R.id.irrigation);
        scheme = findViewById(R.id.scheme);
        disease = findViewById(R.id.disease);
        chatbot = findViewById(R.id.chatbot);
        forum = findViewById(R.id.forum);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });

        olx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ImagesActivity.class));
            }
        });

        irrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Irrigation.class));
            }
        });

        scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Education.class));
            }
        });

        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });

        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Chatbot.class));
            }
        });

        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForumActivity.class));
            }
        });



    }

   /* ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };*/
}
