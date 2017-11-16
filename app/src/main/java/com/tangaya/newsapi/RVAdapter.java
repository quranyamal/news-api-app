package com.tangaya.newsapi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by amal on 11/16/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NewsViewHolder>{

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView newsImage;
        TextView newsTitle;


        NewsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            newsImage = (ImageView)itemView.findViewById(R.id.news_image);
            newsTitle = (TextView)itemView.findViewById(R.id.title_text);
        }

    }

    List<News> listOfNews;

    RVAdapter(List<News> listOfNews){
        this.listOfNews = listOfNews;
    }

    @Override
    public int getItemCount() {
        return listOfNews.size();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_card, viewGroup, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        newsViewHolder.newsImage.setImageResource(R.drawable.turkey800);
        newsViewHolder.newsTitle.setText(listOfNews.get(i).getTitle());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
