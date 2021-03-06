package com.example.dhgatetest2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.search.SearchActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(SearchActivity.SEARCH_LISTENER_CLASS, MySearchListener.class.getName());
        intent.putExtra(SearchActivity.DEFAULT_CACHE_SIZE,10);
        startActivity(intent);
    }


}
