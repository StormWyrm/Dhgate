package com.example.dhgatetest2.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.base.SearchListener;
import com.example.dhgatetest2.fragment.HistoryFragment;
import com.example.dhgatetest2.fragment.SearchFragment;
import com.example.dhgatetest2.util.HistoryProvider;
import com.example.dhgatetest2.util.MySearchView;
import com.example.dhgatetest2.util.ThreadUtils;
import com.example.dhgatetest2.util.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/22 22:21
 * @DESC: 搜索的主界面
 * @VERSION: V1.0
 */
public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    public static final String SEARCH_LISTENER_CLASS = "SearchListenerClass";
    public static final String DEFAULT_CACHE_SIZE = "DefaultCacheSize";

    private MySearchView searchView;
    private Timer timer;
    public SearchListener searchListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initData();
        initListener();
    }

    //初始化View
    private void initUi() {
        setContentView(R.layout.activity_search);
        searchView = (MySearchView) findViewById(R.id.searchView);

    }

    //初始化数据
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


    //初始化监听器
    private void initListener() {
        searchView.setOnQueryTextListener(new MySearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(SearchActivity.this, "搜索：" + query, Toast.LENGTH_SHORT).show();
                HistoryProvider.getInstance(SearchActivity.this).add(query);
                if (searchListener != null) {
                    searchListener.click(query);
                }
                return false;
            }

            @Override
            public void onQueryTextChange(final String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Log.d(TAG, "onQueryTextChange: 显示历史界面");
                    showHistoryFragment();
                } else {
                    Log.d(TAG, "onQueryTextChange: 改变到" + newText);
                    delayedTask(newText);
                }

            }
        });
    }

    //返回按钮事件
    public void back(View view) {
        finish();
    }

    //搜索按钮事件
    public void search(View view) {
        String value = searchView.getText();
        if (!TextUtils.isEmpty(value)) {
            HistoryProvider.getInstance(this).add(value);

            if (searchListener != null) {
                searchListener.click(value);
            }
        } else {
            ToastUtils.showToast(this, "搜索不能为空");
        }
    }

    //执行延时任务
    private void delayedTask(final String newText) {

        if (timer == null) {
            startTask(newText);
        } else {
            timer.cancel();
            timer = null;
            startTask(newText);
        }

    }

    //延时任务
    private void startTask(final String newText) {
        timer = new Timer("delayedTask");
        Log.d(TAG, "delayedTask: 开始延时任务");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: 5秒后开始执行任务: " + newText);
                        showSearchFragment(newText);
                    }
                });
            }
        }, 500);
    }

    //切换搜索历史界面
    private void showHistoryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl, new HistoryFragment());
        transaction.commit();
    }

    //切换搜索详情界面
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
