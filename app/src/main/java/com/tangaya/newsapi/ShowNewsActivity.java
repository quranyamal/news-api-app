package com.tangaya.newsapi;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ShowNewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        WebView myWebView = (WebView) findViewById(R.id.news_webview);
        String url = getIntent().getStringExtra("url");
        myWebView.loadUrl(url);

    }
}
