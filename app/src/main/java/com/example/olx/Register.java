package com.example.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.olx.ImagesActivity.aadhar;

public class Register extends AppCompatActivity {

    EditText user_aadhar;
    EditText user_name;
    EditText user_mobile;
    EditText user_address;
    EditText user_category;
    EditText user_password;
    Button user_register;
    DatabaseReference mDatabase;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        user_aadhar = findViewById(R.id.user_aadhar);
        user_name= findViewById(R.id.user_name);
        user_mobile = findViewById(R.id.user_mobile);
        user_address = findViewById(R.id.user_address);
        user_category = findViewById(R.id.user_category);
        user_password = findViewById(R.id.user_password);
        user_register = findViewById(R.id.user_register);
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");


        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String caadhar = user_aadhar.getText().toString().trim();
                String cname = user_name.getText().toString().trim();
                String cmobile = user_mobile.getText().toString().trim();
                String caddress = user_address.getText().toString().trim();
                String ccategory = user_category.getText().toString().trim();
                String cpassword = user_password.getText().toString().trim();

                if(TextUtils.isEmpty(caadhar)){
                    user_aadhar.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(cname)){
                    user_name.setError("Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(cmobile)){
                    user_mobile.setError("Mobile Number is Required.");
                    return;
                }
                if(TextUtils.isEmpty(caddress)){
                    user_address.setError("Address is Required.");
                    return;
                }
                if(TextUtils.isEmpty(ccategory)){
                    user_category.setError("Category is Required.");
                    return;
                }
                if(TextUtils.isEmpty(cpassword)){
                    user_password.setError("Password is Required.");
                    return;
                }

                Map<String, Object> data = new HashMap<>();
                data.put("aadhar", caadhar);
                data.put("name", cname);
                data.put("mobile", cmobile);
                data.put("location", caddress);
                data.put("category", ccategory);
                data.put("password", cpassword);



                db.collection("users").document(caadhar)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Register unSuccessfully", Toast.LENGTH_LONG).show();
                            }
                        });


               // RegisterModel register = new RegisterModel(caadhar,cname,cmobile, caddress,ccategory,cpassword);
                //mDatabase.child(caadhar).setValue(register);



            }
        });


    }
}
