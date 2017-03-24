package com.example.dhgatetest2.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.framework.SearchListener;
import com.example.dhgatetest2.search.HistoryProvider;
import com.example.dhgatetest2.search.SearchActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/22 22:15
 * @DESC: Fragment的基类
 * @VERSION: V1.0
 */
public class BaseFragment extends Fragment {
    protected ListView mListView;
    protected TextView mTextView;

    protected HistoryProvider historyProvider;
    protected SearchActivity mActivity;
    protected SearchListener searchListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (SearchActivity) context;
        searchListener = mActivity.searchListener;
        historyProvider = HistoryProvider.getInstance(mActivity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mListView = (ListView) view.findViewById(R.id.lv_fragment);
        mTextView = (TextView) view.findViewById(R.id.tv_fragment);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    protected void initListener() {
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //如果软键盘开启 关闭软键盘
                if (mActivity.getCurrentFocus() != null) {
                    ((InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(mActivity.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
    }

    protected void initData() {
    }

}
