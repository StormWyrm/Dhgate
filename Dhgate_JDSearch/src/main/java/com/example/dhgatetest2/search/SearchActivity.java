package com.example.dhgatetest2.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.framework.SearchListener;
import com.example.dhgatetest2.search.ui.widget.MySearchView;
import com.example.dhgatetest2.search.util.ThreadUtils;
import com.example.dhgatetest2.search.util.ToastUtils;

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

        if (intent != null) {
            try {
                String className = intent.getStringExtra(SEARCH_LISTENER_CLASS);
                Class<SearchListener> clazz = (Class<SearchListener>) Class.forName(className);
                searchListener = clazz.newInstance();
                int defaultCacheSize = intent.getIntExtra(DEFAULT_CACHE_SIZE, 20);
                HistoryProvider.getInstance(this).initDefaultCacheSize(defaultCacheSize);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("传递的SearchListener异常");
            }
        } else {
            throw new RuntimeException("没有传递SearchListener");
        }
        openSoftKeybroad();//开启软键盘
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
                    searchListener.click(query,SearchListener.SEARCH_FROM_HOT);
                }
                return false;
            }

            @Override
            public void onQueryTextChange(final String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Log.d(TAG, "onQueryTextChange: 显示历史界面");
                    showHistoryFragment();
                }
                Log.d(TAG, "onQueryTextChange: 改变到" + newText);
                delayedTask(newText);


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
                searchListener.click(value,SearchListener.SEARCH_FROM_HOT);
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

                        //当newText为空，HistoryFrament显示时,线程中最后一个搜索可能没有执行完成
                        if (TextUtils.isEmpty(newText)) {
                            Log.d(TAG, "onQueryTextChange: 显示历史界面");
                            showHistoryFragment();
                        }
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

    //开启软键盘
    private void openSoftKeybroad() {
        EditText editText = searchView.getEditText();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
