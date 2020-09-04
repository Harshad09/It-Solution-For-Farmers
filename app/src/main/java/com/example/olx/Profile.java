package com.example.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Profile extends AppCompatActivity {

    public RecyclerView myphoto;
    public TextView updateProfile;
    public TextView name;
    private ProfileAdapter mAdapter;
    public TextView mobile;
    public TextView location;
    public TextView olxdetail;
    private List<ImageProfileModel> mUploads;
    private DatabaseReference myDatabaseRef;
    private DatabaseReference mDatabaseRef;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);


        //data


        myphoto = findViewById(R.id.image_view);
        myphoto.setHasFixedSize(true);
        myphoto.setLayoutManager(new LinearLayoutManager(this));
        updateProfile = findViewById(R.id.update_photo);
        mUploads = new ArrayList<>();
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        location = findViewById(R.id.location);
        olxdetail = findViewById(R.id.olxdetail);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        //name.setText("Name : "+aadhar);


        //fetch my photo
        //String path = "profile/"+aadhar ;
        myDatabaseRef = FirebaseDatabase.getInstance().getReference("profile");
        String aname = "sagar";

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("aadhar","");



        myDatabaseRef.orderByChild("mAadhar").equalTo(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ImageProfileModel upload = postSnapshot.getValue(ImageProfileModel.class);
                    mUploads.add(upload);
                }
                mAdapter = new ProfileAdapter(Profile.this, mUploads);
                myphoto.setAdapter(mAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        //fetch end


        //fetch data from firestore

        db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("users");

        DocumentReference doc = docRef.document(value);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //String userpassword = password.getText().toString().trim();

                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists())
                    {
                        String sname = document.getString("name");
                        String smobile = document.getString("mobile");
                        String slocation = document.getString("location");
                        name.setText("Name : "+sname);
                        mobile.setText("Mobile No. : "+smobile);
                        location.setText("Address : "+slocation);
                    }
                    else {

                        Toast.makeText(Profile.this, "No user with aadhar", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Profile.this, "Something is Wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });



        //second metgod

        //CollectionReference citiesRef = db.collection("users");
        /*Query first = db.collection("users").whereEqualTo("aadhar",aadhar);


        // [START chain_filters]
        first.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots)
                    {
                        for (QueryDocumentSnapshot snap : documentSnapshots) {
                           // Log.d(TAG, snap.getId() + " => " + snap.getData());

                            name.setText("Name : "+snap.getString("name"));
                            //mobile.setText("Mobile No. : "+snap.get("mobile"));
                            //location.setText("Address : "+snap.get("location"));
                        }
                    }
                });*/



        //third method


        //fetch data end




        //onclick
        olxdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),olxselldetail.class));
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UploadProfile.class));
            }
        });

        //onclick end
        //data end




    }

    @Override
    public void onBackPressed()
    {

        Intent intent=new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
        finish();

    }

}
