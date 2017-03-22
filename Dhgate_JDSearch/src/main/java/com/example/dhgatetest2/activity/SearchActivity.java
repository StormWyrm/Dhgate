package com.example.dhgatetest2.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.base.SearchListener;
import com.example.dhgatetest2.fragment.HistoryFragment;
import com.example.dhgatetest2.fragment.SearchFragment;
import com.example.dhgatetest2.util.HistoryProvider;
import com.example.dhgatetest2.util.ThreadUtils;
import com.example.dhgatetest2.util.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    public static final String SEARCH_LISTENER_CLASS = "SearchListenerClass";
    public static final String DEFAULT_CACHE_SIZE = "DefaultCacheSize";

    private SearchView searchView;
    private Timer timer;
    public SearchListener searchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initData();
        initListener();
    }


    private void initUi() {
        setContentView(R.layout.activity_search);
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
        searchView = (SearchView) findViewById(R.id.searchView);
    }

    private void initData() {
        Intent intent = getIntent();
        int defaultCacheSize = 20;
        if (intent != null) {
            String className = intent.getStringExtra(SEARCH_LISTENER_CLASS);
            try {
                Class<SearchListener> clazz = (Class<SearchListener>) Class.forName(className);
                searchListener = clazz.newInstance();
                defaultCacheSize = intent.getIntExtra(DEFAULT_CACHE_SIZE, 20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HistoryProvider.getInstance(this).initDefaultCacheSize(defaultCacheSize);
        showHistoryFragment();
    }


    private void initListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this, "搜索：" + query, Toast.LENGTH_SHORT).show();
                HistoryProvider.getInstance(SearchActivity.this).add(query);
                if (searchListener != null) {
                    searchListener.click(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Log.d(TAG, "onQueryTextChange: 显示历史界面");
                    showHistoryFragment();
                } else {
                    delayedTask(newText);
                }
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
        String value = searchView.getQuery().toString();
        ToastUtils.showToast(this, "搜索:" + value);
        HistoryProvider.getInstance(this).add(value);

        if (searchListener != null) {
            searchListener.click(value);
        }
    }


    /**
     * 延时执行任务
     */
    private void delayedTask(final String newText) {
        if (timer == null) {
            startTask(newText);
        } else {
            timer.cancel();
            timer = null;
            Log.d(TAG, "delayedTask: 取消延时任务");
            startTask(newText);
        }

    }

    /**
     * 任务
     *
     * @param newText
     */
    private void startTask(final String newText) {
        timer = new Timer("delayedTask");
        Log.d(TAG, "delayedTask: 开始延时任务");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: 5秒后开始执行任务");
                        showSearchFragment(newText);
                    }
                });
            }
        }, 500);
    }

    /**
     * 显示历史搜索界面
     */
    private void showHistoryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl, new HistoryFragment());
        transaction.commit();
    }

    /***
     * 显示搜索界面
     * @param newText
     */
    private void showSearchFragment(String newText) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("newText", newText);
        searchFragment.setArguments(args);
        transaction.replace(R.id.fl, searchFragment);
        transaction.commit();

    }

}
