package com.example.olx;

import android.content.Intent;


import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


import java.util.List;


import adapter.HomeAdapter;
import model.HomeModel;





public class ForumActivity extends AppCompatActivity {

    public static String currentUser = "sagar";


    private Toolbar toolbar;
    private RecyclerView feedRecyclerView;
    private RecyclerView.Adapter mDataAdapter;
    private ProgressBar progressBar;
    private ImageView error;


    public String userId,questionid;
    public HomeModel homeModel = new HomeModel();
    public List<HomeModel> entity = new ArrayList<>();
    public List<String> questionIdList = new ArrayList<>();
    public List<String > useridList = new ArrayList<>();


    //firebase obj
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();//getting instance of the firebase storage

    StorageReference storageRef = storage.getReference();// Create a storage reference from our app


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Home </font>"));
        setContentView(R.layout.activity_forum);

        error = findViewById(R.id.error);

        feedRecyclerView = findViewById(R.id.recyclerview);


        //Fetching data in the main feedRecyclerView that is questions and answers
        //setting adapter and recyclerview


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter = new HomeAdapter(this,entity,questionIdList,useridList);

        feedRecyclerView.setAdapter(mDataAdapter);

        progressBar =  findViewById(R.id.progressBarMain1);
        progressBar.setVisibility(View.VISIBLE);


        db.collectionGroup("question").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("errorL", "Listen failed.", e);
                    return;
                }
                entity.clear();
                questionIdList.clear();
                useridList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        progressBar.setVisibility(View.INVISIBLE);
                        homeModel = doc.toObject(HomeModel.class);
                        entity.add(homeModel);

                        questionid = doc.getId();
                        questionIdList.add(questionid);

                        userId = doc.getReference().getParent().getParent().getId();
                        useridList.add(userId);

                }
                mDataAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.search:
                startActivity(new Intent(getApplicationContext(),Search.class));
                overridePendingTransition(0,0);
                return true;

            case R.id.ask:
                startActivity(new Intent(getApplicationContext(),AddQuestion.class));
                overridePendingTransition(0,0);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }




}