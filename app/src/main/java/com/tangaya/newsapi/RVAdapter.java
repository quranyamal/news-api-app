package com.tangaya.newsapi;

import android.content.Context;
import android.database.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by amal on 11/16/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NewsViewHolder>{

    List<Article> articles;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        CardView cv;
        TextView newsTitle;
        //TextView newsSource;

        NewsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.news_card);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            newsTitle = (TextView) itemView.findViewById(R.id.title_text);
            //newsSource = (TextView) itemView.findViewById(R.id.source_text);
        }
    }

    RVAdapter(List<Article> articles){
        this.articles = articles;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_card, viewGroup, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        if (articles.get(i).getUrlToImage()!=null) {
            new DownloadImageTask(newsViewHolder.newsImage)
                    .execute(articles.get(i).getUrlToImage());
        }
        newsViewHolder.newsTitle.setText(articles.get(i).getTitle());
        //newsViewHolder.newsSource.setText(articles.get(i).getSource().getName());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}