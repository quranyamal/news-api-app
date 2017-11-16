package com.tangaya.newsapi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class MenuActivity extends Activity {

    ArrayList<HashMap<String, String>> articleList;
    RecyclerView mRecyclerView;
    RVAdapter adapter;

    TextView jsonText;
    String jsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        jsonText = (TextView) findViewById(R.id.json_text);

        articleList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    public void getJson(View view) {
        GetArticles ga = new GetArticles();
        ga.execute();
        jsonText.setText("loading");
    }

    public void checkParsing(View view) {
        if (jsonStr == null) {
            Toast.makeText(getApplicationContext(), "press get json first", Toast.LENGTH_SHORT).show();
        } else {
            jsonText.setText(articleList.get(1).toString());
        }
    }

    public void gotoNews(View view) {
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }

    private class GetArticles extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MenuActivity.this,"Json Data is downloading",
                    Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String apiUrl = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=411b30da03f24aed986d77e276aec72e";
            jsonStr = sh.makeServiceCall(apiUrl);


            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String status= jsonObj.getString("status");
                    String source = jsonObj.getString("source");
                    String sortBy = jsonObj.getString("sortBy");

                    // Getting JSON Array node
                    JSONArray articles = jsonObj.getJSONArray("articles");

                    // looping through All Contacts
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject c = articles.getJSONObject(i);
                        String author = c.getString("author");
                        String title = c.getString("title");
                        String description = c.getString("description");
                        String url = c.getString("url");
                        String publishedAt = c.getString("publishedAt");


                        // tmp hash map for single contact
                        HashMap<String, String> articleHashMap = new HashMap<>();

                        // adding each child node to HashMap key => value
                        articleHashMap.put("author", author);
                        articleHashMap.put("title", title);
                        articleHashMap.put("description", description);
                        articleHashMap.put("url", url);
                        articleHashMap.put("publishedAt", publishedAt);

                        // adding contact to contact list
                        articleList.add(articleHashMap);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            jsonText.setText(jsonStr);
//            ListAdapter adapter = new SimpleAdapter(MenuActivity.this, articleList,
//                    R.layout.news_card, new String[]{ "image","title"},
//                    new int[]{R.id.news_image, R.id.title_text});
//            adapter = new RVAdapter(articleList);
//            mRecyclerView.setAdapter(adapter);
        }
    }

}
