package com.example.newsapproomorm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.newsapproomorm.data.NewsItem;
import com.example.newsapproomorm.data.NewsItemViewModel;
import com.example.newsapproomorm.sync.FirebaseUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<NewsItem> articles = new ArrayList<>();
    private NewsRecyclerViewAdapter mAdapter;
    private NewsItemViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUtils.scheduleDatabaseSync(this);

        mViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mAdapter = new NewsRecyclerViewAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel.getAllNews().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                populateRecyclerView(newsItems);
            }
        });

        mViewModel.sync();
        Log.d(TAG, "asdfasdfasdfasdf");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            mViewModel.sync();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateRecyclerView(List<NewsItem> newsItems) {
        Log.d(TAG, "parse news");
        mAdapter.mArticles.addAll(newsItems);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "adapter size = " + mAdapter.getItemCount());
    }


}
