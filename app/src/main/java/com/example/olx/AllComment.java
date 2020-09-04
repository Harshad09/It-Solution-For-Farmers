package com.example.olx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.AllCommentAdapter;

public class AllComment extends AppCompatActivity {

    private List<String> comment = new ArrayList<>();
    private String question;
    private RecyclerView feedRecyclerView;
    private RecyclerView.Adapter mDataAdapter;

    private TextView que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>All Comments </font>"));
        setContentView(R.layout.activity_all_commit);



        feedRecyclerView = findViewById(R.id.commentrecycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter = new AllCommentAdapter(this,comment);

        feedRecyclerView.setAdapter(mDataAdapter);

        ArrayList<String> answerArray = getIntent().getStringArrayListExtra("answerArray");
        question = getIntent().getStringExtra("question");

        que = findViewById(R.id.question);
        que.setText(question);



        for (String answer : answerArray){
            comment.add(answer);
        }
//        Log.d("anso",answerArray.toString());
        mDataAdapter.notifyDataSetChanged();




    }
}
