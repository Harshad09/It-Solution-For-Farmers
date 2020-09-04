package com.example.olx;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapter.EducationAdapter;
import model.EducationModel;

public class Education extends AppCompatActivity {

    Button myscheme;
    SearchView ssearchView;
    private List<EducationModel> entity;
    private RecyclerView sRecyclerView ;
    //    SearchView searchView;
//    Button filter;
    String filterName;



    //firebase obj
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EducationAdapter mDataAdapter;
    EducationModel educationModel;
    List<EducationModel> EducationList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Schemes</font>"));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_education);

        //eduRecyclerView = findViewById(R.id.educationRecyclerView);
//        filter = findViewById(R.id.filterBtn);

//        searchView = findViewById(R.id.searchView);
        sRecyclerView = findViewById(R.id.educationRecyclerView);
        sRecyclerView.setHasFixedSize(true);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        entity = new ArrayList<>();
        ssearchView =  findViewById(R.id.searchView1);
        filterName = "filter";
        myscheme = findViewById(R.id.myscheme);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter = new EducationAdapter(this,EducationList);
        sRecyclerView.setAdapter(mDataAdapter);


        myscheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Myscheme.class));
            }
        });

        db.collection("Government Schemes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                educationModel = document.toObject(EducationModel.class);
                                EducationList.add(educationModel);
                                Log.d("Government Schemes", document.getId() + " => " + document.getData());
                            }
                            mDataAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("Government Schemes", "Error getting documents: ", task.getException());
                        }
                    }
                });






    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ssearchView != null) {
            ssearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    search(s);
                    return true;
                }


            });
        }


    }

    private void search(String str)
    {
        ArrayList<EducationModel> myList = new ArrayList<>();
        for(EducationModel object : entity)
        {
            if(object.getName().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }

        EducationAdapter searchimage = new EducationAdapter(Education.this,myList);
        sRecyclerView.setAdapter(searchimage);


    }

}