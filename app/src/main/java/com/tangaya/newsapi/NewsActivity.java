package com.tangaya.newsapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class NewsActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<News> arrayOfNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        arrayOfNews = new ArrayList<News>();
        fetchNews();

        RVAdapter adapter = new RVAdapter(arrayOfNews);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(adapter);

        // use a linear layout manager
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(llm);

    }

    void fetchNews() {
        News news1 = new News("@drawable/turkey800", "Forgotten History", "R.drawable.turkey800");
        News news2 = new News("@drawable/london800", "London Bridge", "R.drawable.london800");
        News news3 = new News("@drawable/turkey800", "The City of Istanbul", "R.drawable.turkey800");

        arrayOfNews.add(news1);
        arrayOfNews.add(news2);
        arrayOfNews.add(news3);
    }
}
