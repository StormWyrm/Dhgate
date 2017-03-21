package com.example.dhgatetest2.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.fragment.HistoryFragment;
import com.example.dhgatetest2.fragment.SearchFragment;
import com.example.dhgatetest2.util.HistoryProvider;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private boolean isHistoryPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initData("");
        initListener();
    }


    private void initUi() {
        setContentView(R.layout.activity_search);
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
        searchView = (SearchView) findViewById(R.id.searchView);

    }

    /***
     * 搜索和历史Fragment的切换
     */
    private void initData(String newText) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isHistoryPage) {
            transaction.replace(R.id.fl, new HistoryFragment());
        } else {
            SearchFragment searchFragment = new SearchFragment();
            Bundle args = new Bundle();
            args.putString("newText", newText);
            searchFragment.setArguments(args);
            transaction.replace(R.id.fl, searchFragment);
        }
        transaction.commit();
    }

    private void initListener() {
        final HistoryProvider historyProvider = new HistoryProvider(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
                historyProvider.add(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(SearchActivity.this, newText, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(newText)) {
                    isHistoryPage = true;
                } else {
                    isHistoryPage = false;
                }
                initData(newText);
                return false;
            }
        });
    }

    /**
     * 左上方点击返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 右上方搜索点击
     *
     * @param view
     */
    public void search(View view) {
        Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
    }


}
