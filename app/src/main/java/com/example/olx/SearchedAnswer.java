package com.example.olx;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchedAnswerAdapter;
import model.SearchedAnswerModel;

public class SearchedAnswer extends AppCompatActivity {

    private String question,tag1,tag2,tag3,userid,questionid;
    private TextView tag1view,tag2view,tag3view,question_display;


    private List<SearchedAnswerModel> AnswerList = new ArrayList<>();
    private SearchedAnswerModel searchedAnswerModel = new SearchedAnswerModel();
    public List<String> answerId = new ArrayList<>();
    public List<String > Userid = new ArrayList<>();


    private RecyclerView feedRecyclerView;
    private RecyclerView.Adapter mDataAdapter;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();//getting instance of the firebase storage

    StorageReference storageRef = storage.getReference();// Create a storage reference from our app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Result </font>"));

        setContentView(R.layout.activity_searched_answer);

        feedRecyclerView = findViewById(R.id.searchedAnswerRecycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter = new SearchedAnswerAdapter(this,AnswerList,answerId,Userid);

        feedRecyclerView.setAdapter(mDataAdapter);





        question = getIntent().getStringExtra("question");
        tag1 = getIntent().getStringExtra("tag1");
        tag2 = getIntent().getStringExtra("tag2");
        tag3 = getIntent().getStringExtra("tag3");
        userid = getIntent().getStringExtra("userid");
        questionid = getIntent().getStringExtra("questionid");

        tag1view = findViewById(R.id.tag_1);
        tag2view = findViewById(R.id.tag_2);
        tag3view = findViewById(R.id.tag_3);
        question_display = findViewById(R.id.question_display);

        question_display.setText(question);
        tag1view.setText(tag1);
        tag2view.setText(tag2);
        tag3view.setText(tag3);

        db.collectionGroup("answer").whereEqualTo("queId",questionid).orderBy("upvotes", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("errorL", "Listen failed.", e);
                            return;
                        }
                        AnswerList.clear();
                        answerId.clear();
                        Userid.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.exists()) {
                                searchedAnswerModel = doc.toObject(SearchedAnswerModel.class);

                                AnswerList.add(searchedAnswerModel);
                                answerId.add(doc.getId());

                                String userId = doc.getReference().getParent().getParent().getId();
                                Userid.add(userId);
                            }
                        }
                        mDataAdapter.notifyDataSetChanged();
                    }
                });


    }
}