package com.tangaya.newsapi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class NewsActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Article> articles;
    String newsJson;
    RecyclerView newsRV;
    CardView newsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        articles = new ArrayList<Article>();
        Log.d("NJS", getIntent().getStringExtra("newsJson"));

        fetchNews();

        newsRV = (RecyclerView) findViewById(R.id.news_recycler);
        newsCard = (CardView) findViewById(R.id.news_card);
    }

    public void showArticle(View view) {
        int itemPosition = newsRV.indexOfChild(view);
        Toast.makeText(getApplicationContext(), "opening article pos: " +
                String.valueOf(itemPosition), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), ShowNewsActivity.class);
        intent.putExtra("url", "https://www.bostonglobe.com/business/talking-points/2017/12/19/home-drone-delivery-step-closer-reality/XNKELcdfvrbAQDpn9TehRI/story.html");
        //startActivity(intent);

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);

        return true;
    }

    void fetchNews() {
        //String  URL = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=411b30da03f24aed986d77e276aec72e";
        Toast.makeText(getApplicationContext(), "fetching json", Toast.LENGTH_SHORT).show();

        if (getIntent().hasExtra("newsJson")) {
            newsJson = getIntent().getStringExtra("newsJson");

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson =  gsonBuilder.create();
            News news = gson.fromJson(newsJson, News.class);

            Log.d("CODE", newsJson);

            for (int i=0; i<news.getArticles().size(); i++) {
                articles.add(news.getArticles().get(i));
            }

            RVAdapter adapter = new RVAdapter(articles);

            mRecyclerView = (RecyclerView) findViewById(R.id.news_recycler);
            mRecyclerView.setAdapter(adapter);

            // use a linear layout manager
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(llm);
        } else {
            Toast.makeText(getApplicationContext(), "no json", Toast.LENGTH_SHORT).show();
        }
    }
}
