package com.example.olx;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyContent extends AppCompatActivity {

    Toolbar toolbar;
    private Button fragmentAns,fragmentQue;
//    private List<ContentAnswerModel> allAns = new ArrayList<>();
//    private List<ContentQuestionModel> allQue = new ArrayList<>();
//    private ContentAnswerModel contentAnswerModel = new ContentAnswerModel();
//    private ContentQuestionModel contentQuestionModel = new ContentQuestionModel();
//    private RecyclerView recyclerViewAns;
//    private RecyclerView recyclerViewQue;
//    private RecyclerView.Adapter mDatapter;

//    //firebase obj
//    // Access a Cloud Firestore instance from your Activity
//    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("My Content");
//        toolbar.setLogo(R.drawable.account);

//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>My Content </font>"));

        setContentView(R.layout.activity_my_content);


//        //Fetching all the answer of the user itself
//        DocumentReference userQuestion = db.collection("Users").document("user3");
//        userQuestion.collection("answer").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if (task.isSuccessful()){
//                    for (QueryDocumentSnapshot document : task.getResult()){
//                        ContentAnswerModel contentAnswerModel = document.toObject(ContentAnswerModel.class);
////                        Log.d("content", document.getId() + " => " + document.getData());
////                        allAns.add(contentAnswerModel);
//
//
//                    }
//
//                }
//            }
//        });
//
//        //Fetching all the question of the user itself
//        DocumentReference userAnswer = db.collection("Users").document("user3");
//        userAnswer.collection("question").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if (task.isSuccessful()){
//                    for (QueryDocumentSnapshot document : task.getResult()){
//                        ContentQuestionModel contentQuestionModel = document.toObject(ContentQuestionModel.class);
//                       // Log.d("content", document.getId() + " => " + document.getData());
//                        allQue.add(contentQuestionModel);
//                    }
//                }
//            }
//        });


//       Adding fragments

        fragmentAns = findViewById(R.id.btn_ansFrag);
        fragmentQue = findViewById(R.id.btn_queFrag);



        fragmentAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentAns = new MyContentAnswer();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,fragmentAns);
                fragmentTransaction.commit();
            }
        });

        fragmentQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentQue = new MyContentQuestion();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,fragmentQue);
                fragmentTransaction.commit();

            }
        });



        //Initialized and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        //Set my_content selected
        bottomNavigationView.setSelectedItemId(R.id.my_content);

        //Perform ItemSelectListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_answer:
                        startActivity(new Intent(getApplicationContext(), AddAnswer.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.my_content:
                        return true;

//                    case R.id.notification:
//                        startActivity(new Intent(getApplicationContext(),Notification.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ForumActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
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
                startActivity(new Intent(getApplicationContext(), Search.class));
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