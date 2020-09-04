package com.example.olx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;




public class ImagesActivity extends AppCompatActivity{


    static String aadhar;
    private RecyclerView mRecyclerView;
    private ImageView log;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference sDatabaseRef;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    SearchView searchView;
    String[] stringArray2 = {"all","Cultivator","Weeder","Sprayer","Driller","Gardner"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_images);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        log = findViewById(R.id.logout);
        searchView =  findViewById(R.id.searchView);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        //spinner
        Spinner spin =  findViewById(R.id.drop);
        spin.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
            int position, long id) {
                String fetch_by_category = stringArray2[position];
                Toast.makeText(getApplicationContext(),fetch_by_category , Toast.LENGTH_LONG).show();

                String weeder = "weeder";
                String sprayer = "sprayer";
                String all = "all";


                if(all.equalsIgnoreCase(fetch_by_category))
                {
                    mUploads.clear();
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Upload upload = postSnapshot.getValue(Upload.class);
                                mUploads.add(upload);
                            }
                            mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    });

               }
               else if(sprayer.equalsIgnoreCase(fetch_by_category))
                    {
                        mUploads.clear();
                    sDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
                    sDatabaseRef.orderByChild("mName").equalTo("Sprayer").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Upload upload = postSnapshot.getValue(Upload.class);
                                mUploads.add(upload);
                            }
                            mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                else if(weeder.equalsIgnoreCase(fetch_by_category))
                {
                    mUploads.clear();
                    sDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
                    sDatabaseRef.orderByChild("mName").equalTo("Weeder").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Upload upload = postSnapshot.getValue(Upload.class);
                                mUploads.add(upload);
                            }
                            mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                else if(fetch_by_category.equalsIgnoreCase("Driller"))
                {
                    mUploads.clear();
                    sDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
                    sDatabaseRef.orderByChild("mName").equalTo("Driller").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Upload upload = postSnapshot.getValue(Upload.class);
                                mUploads.add(upload);
                            }
                            mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                else if(fetch_by_category.equalsIgnoreCase("Cultivator"))
                {
                    mUploads.clear();
                    sDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
                    sDatabaseRef.orderByChild("mName").equalTo("Cultivator").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Upload upload = postSnapshot.getValue(Upload.class);
                                mUploads.add(upload);
                            }
                            mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                else if(fetch_by_category.equalsIgnoreCase("Gardner"))
                {
                    mUploads.clear();
                    sDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
                    sDatabaseRef.orderByChild("mName").equalTo("Gardner").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Upload upload = postSnapshot.getValue(Upload.class);
                                mUploads.add(upload);
                            }
                            mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressCircle.setVisibility(View.INVISIBLE);
                        }
                    });

                }
               else
                {
                    Toast.makeText(ImagesActivity.this,"No Data Found of this category",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,stringArray2);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        //spinner
        //logout

        //logout end


        //data from intent
        Intent intent = getIntent();
        aadhar = intent.getStringExtra("useraadhar");

        /*SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("aadhar", aadhar);
        editor.apply();*/
        //data end


        //bottomnav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        return true;

                    case R.id.sell:
                        startActivity(new Intent(getApplicationContext(),Sell.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.rent:
                        startActivity(new Intent(getApplicationContext(), Rent.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });

        //bottomend



        //fetch data
        /*mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });*/
        //fetch data end

        /*
         <com.synnapps.carouselview.CarouselView
                android:id="@+id/carouselView"
                android:layout_width="match_parent"
                android:layout_height="140dp"
                android:layout_marginEnd="10dp"
                android:layout_marginStart="10dp"
                android:layout_marginTop="10dp"
                app:fillColor="#FFFFFFFF"
                app:pageColor="#00000000"
                app:radius="6dp"
                app:slideInterval="3000"
                app:strokeColor="#FF777777"

                app:strokeWidth="1dp"/>
*/

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(searchView != null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        ArrayList<Upload> myList = new ArrayList<>();
        for(Upload object : mUploads)
        {
            if(object.getmCategory().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }

        ImageAdapter searchimage = new ImageAdapter(ImagesActivity.this,myList);
        mRecyclerView.setAdapter(searchimage);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy invoked");
    }

    @Override
    public void onBackPressed()
    {

        Intent intent=new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
        finish();

    }




}
