package com.example.dhgatetest2.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.activity.SearchActivity;
import com.example.dhgatetest2.util.HistoryProvider;

/**
 * Created by liqingfeng on 2017/3/21.
 */

public class BaseFragment extends Fragment {
    protected SearchActivity mActivity;
    protected SearchListener searchListener;
    protected ListView mListView;
    protected TextView mTextView;
    protected HistoryProvider historyProvider;

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

    }

    protected void initData() {
    }

}
