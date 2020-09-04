package com.example.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
{


    EditText aadhar,password;
    LinearLayout login;
    FirebaseFirestore db;
    private String TAG;
    TextView register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        /*TextView registergo = findViewById(R.id.textView4) ;
        SpannableString content = new SpannableString( "Register" ) ;
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 ) ;
        registergo.setText(content) ;

        registergo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });*/




        register = findViewById(R.id.register);
        aadhar = findViewById(R.id.aadhar);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        db = FirebaseFirestore.getInstance();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });



        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //startActivity(new Intent(getApplicationContext(),Home.class));
                final String useraadhar = aadhar.getText().toString().trim();
                final String userpassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(useraadhar) || TextUtils.isEmpty(userpassword)){
                    aadhar.setError("Email  Or Passwordis Required.");
                    return;
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);

                    //DocumentReference docRef = db.collection("users").document(useraadhar);


                DocumentReference docRef = db.collection("users").document(useraadhar);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            String pw = document.getString("password");
                            if (document.exists())
                            {
                                //Log.d(TAG, "DocumentSnapshot data: " + document.getString("password") );
                                if( pw.equalsIgnoreCase(userpassword))
                                {
                                    SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("aadhar", useraadhar);
                                    editor.apply();
                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    Intent intent1 = new Intent(MainActivity.this, Sell.class);
                                    //startActivity(new Intent(getApplicationContext(),Home.class));
                                    //Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    intent.putExtra("useraadhar",useraadhar);
                                    intent1.putExtra("myaadhar",useraadhar);
                                    startActivity(intent);

                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Password is Wrong", Toast.LENGTH_SHORT).show();

                                }

                            }
                            else {

                                Toast.makeText(MainActivity.this, "No user with aadhar", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Something is Wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                }








            }

        });


    }
}
