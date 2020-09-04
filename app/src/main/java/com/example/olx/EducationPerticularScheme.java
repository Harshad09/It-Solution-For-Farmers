package com.example.olx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import model.EducationModel;

public class EducationPerticularScheme extends AppCompatActivity {

    TextView perticularSchemeName,perticularSchemeOverview,eligible,perticularSchemeDoc;
    Button link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_education_perticular_scheme);

        EducationModel educationModel= new EducationModel();

        final String name,eligibility,overview,requireddocuments,addressUrl;

        name = getIntent().getStringExtra("name");
        //department = getIntent().getStringExtra("department");
        overview = getIntent().getStringExtra("overview");
        eligibility = getIntent().getStringExtra("benefits");
        requireddocuments = getIntent().getStringExtra("documentRequired");
        addressUrl = getIntent().getStringExtra("link");

        perticularSchemeName = findViewById(R.id.perticularSchemeName);
        //perticularSchemeDept = findViewById(R.id.perticularSchemeDept);
        perticularSchemeOverview = findViewById(R.id.perticularSchemeOverview);
        eligible = findViewById(R.id.eligible);
        perticularSchemeDoc = findViewById(R.id.perticularSchemeDoc);

        link = findViewById(R.id.link);

        perticularSchemeName.setText(name);
        //perticularSchemeDept.setText(department);
        perticularSchemeOverview.setText(overview);
        eligible.setText(eligibility);
        perticularSchemeDoc.setText(requireddocuments);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(addressUrl));
                startActivity(intent);
            }
        });
    }
}