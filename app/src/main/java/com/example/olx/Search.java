package com.example.olx;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchAdapter;

public class Search extends AppCompatActivity {

    Client client = new Client("8806EZYTK6", "3b6984fb8d2d409b1b87dcbca48a9fbd");
    Index index = client.getIndex("sih");

    private RecyclerView solutionRecycler;
    private SearchView searchView;
    private List<JSONObject> result = new ArrayList<>();
    private RecyclerView.Adapter mDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Search </font>"));
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.askQuestion);
        searchView.setIconifiedByDefault(false);
        solutionRecycler = findViewById(R.id.solutionRecycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        solutionRecycler.setLayoutManager(layoutManager);
        mDataAdapter = new SearchAdapter(this,result);

        solutionRecycler.setAdapter(mDataAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {

                Query query = new Query(text)
                        .setAttributesToRetrieve("question", "tag1","tag2","tag3","questionid","userid")
                        .setHitsPerPage(50);
                index.searchAsync(query, new CompletionHandler() {
                    @Override
                    public void requestCompleted(JSONObject content, AlgoliaException error) {
                        result.clear();
                        Log.d("algolia",content.toString());
                        try {
                            JSONArray hits = content.getJSONArray("hits");

                            for (int i=0;i< hits.length();i++){
                                result.add(hits.getJSONObject(i));
                            }
                            mDataAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("algolia",e.toString());
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}