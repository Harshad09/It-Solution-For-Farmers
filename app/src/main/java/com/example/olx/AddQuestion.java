package com.example.olx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class AddQuestion extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText write_question,tag_1,tag_2,tag_3 ;
    private Button send,ChooseImage;
    private Uri filePath;
    private ImageView relatedImage;
    private String question,tag1,tag2,tag3,currentanswerId;
    ArrayList<String> tags = new ArrayList<>();
    private String YourApplicationID = "8806EZYTK6";
    private String YourAPIKey = "35cdf8c9d0de2b2f730864cd561861b5";
    private String my_index_name ="sih";
    //firebase database instances
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private final int PICK_IMAGE_REQUEST = 22;

    //Algolia search
    Client client = new Client(YourApplicationID, YourAPIKey);
    Index index = client.getIndex(my_index_name);

    private Map<String,Object> insertValues = new HashMap<>();
    private List<String> answer = new ArrayList<>();
    private List<String> upvoters = new ArrayList<>();
    private List<String> user = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                relatedImage.setVisibility(View.VISIBLE);
                relatedImage.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Add Question </font>"));

        setContentView(R.layout.activity_add_question);

        relatedImage = findViewById(R.id.choosedImage);
        ChooseImage = findViewById(R.id.choosed);

        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE_REQUEST);
            }
        });



        send = findViewById(R.id.add_que);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Initializing all views objects
                write_question = findViewById(R.id.addQuestion);



                 question = write_question.getText().toString();
//                currentanswerId = db.collection("users").document(ForumActivity.currentUser).collection("answer").document().getId();


                 if (question.isEmpty()){
                     Toast.makeText(AddQuestion.this, "Please enter valid question", Toast.LENGTH_SHORT).show();
                     return;
                 }

                    if (filePath!=null){
                        final StorageReference imageRef = storageRef.child("images/"+ UUID.randomUUID());
                        imageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        insertValues.put("question",question);
                                        insertValues.put("answer",answer);
                                        insertValues.put("imageUrl",uri.toString());
                                        insertValues.put("upvotes",0);
                                        insertValues.put("upvoters",upvoters);
                                        insertValues.put("user",user);

                                        final String queId =  db.collection("users").document(ForumActivity.currentUser).collection("question").document().getId();
                                        db.collection("users").document(ForumActivity.currentUser).collection("question")
                                                .document(queId).set(insertValues, SetOptions.merge())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(AddQuestion.this, "Question added Successfully", Toast.LENGTH_SHORT).show();
                                                        //Adding data to algolia
                                                        try {
                                                            index.addObjectAsync(new JSONObject()
                                                                    .put("question", question)
                                                                    .put("userid", ForumActivity.currentUser)
                                                                    .put("questionid",queId), null);
                                                        } catch (JSONException e) {
                                                            Toast.makeText(AddQuestion.this, "Sorry !", Toast.LENGTH_SHORT).show();
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
                                    }
                                });
                            }
                        });
                    }

                



            }
        });



    }

}
