package com.tangaya.newsapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuActivity extends Activity implements AdapterView.OnItemSelectedListener{

    ArrayList<HashMap<String, String>> articleList;
    RecyclerView mRecyclerView;
    RVAdapter adapter;
    String source = "not-set";
    String newsJson;
    News news = null;
    boolean isRadioChecked = false;
    String apiURL;
    RadioGroup radioGroup;
    EditText topicEdText;
    Spinner sourceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        topicEdText = (EditText) findViewById(R.id.topic_edit);

        articleList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recycler);

        sourceSpinner = (Spinner) findViewById(R.id.source_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(),
                android.R.layout.simple_spinner_item, getResources().getTextArray(R.array.news_sources));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        sourceSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_baru, menu);
        return true;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedItem = parent.getItemAtPosition(pos).toString();
        source = selectedItem;
        if (pos!=0) {
            Toast.makeText(this, source + " selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this,"nothing selected", Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
        isRadioChecked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.all_headline_radio:
                sourceSpinner.setClickable(false);
                topicEdText.setEnabled(false);
                break;
            case R.id.by_source_radio:
                sourceSpinner.setClickable(true);
                sourceSpinner.requestFocus();
                topicEdText.setEnabled(false);
                break;
            case R.id.by_topic_radio:
                sourceSpinner.setClickable(false);
                topicEdText.setEnabled(true);
                break;
        }
    }

    public void onClickShowNews(View view) {
        if (isRadioChecked) {
            switch(radioGroup.getCheckedRadioButtonId()) {
                case R.id.all_headline_radio:
                    //Toast.makeText(getApplicationContext(), "goto headline", Toast.LENGTH_SHORT).show();
                    gotoHeadlines();
                    break;
                case R.id.by_source_radio:
                    if (sourceSpinner.getSelectedItemPosition()!=0) {
                        gotoNewsBySource(source);
                    } else {
                        Toast.makeText(getApplicationContext(), "select source!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.by_topic_radio:
                    //Toast.makeText(getApplicationContext(), "goto news by topic: " + topicEdText.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (!topicEdText.toString().equals("")) {
                        gotoNewsByTopic(topicEdText.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "input topic!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        } else {
            Toast.makeText(this, "select one option", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoHeadlines() {
        apiURL = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=411b30da03f24aed986d77e276aec72e";
        getJsonAndGo("headline");
    }


    private void gotoNewsBySource(String source) {
        //Log.d("goto", "go to news by source");
        apiURL = "https://newsapi.org/v2/everything?domains="+source+"&apiKey=411b30da03f24aed986d77e276aec72e";
        getJsonAndGo("bySource");
    }

    private void gotoNewsByTopic(String topic) {
        Log.d("goto", "go to news by topic");
        apiURL = "https://newsapi.org/v2/everything?q=" + topic + "&sortBy=publishedAt&apiKey=411b30da03f24aed986d77e276aec72e";
        getJsonAndGo("byTopic");
    }

    public void getJsonAndGo(String type) {
        Log.d("cp", "getjson and go");
        Toast.makeText(getApplicationContext(), "getting json", Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(apiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                Toast.makeText(getApplicationContext(), "json received", Toast.LENGTH_SHORT).show();
                newsJson  = response;

                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                intent.putExtra("newsJson", newsJson);
                Log.d("extra", newsJson);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
