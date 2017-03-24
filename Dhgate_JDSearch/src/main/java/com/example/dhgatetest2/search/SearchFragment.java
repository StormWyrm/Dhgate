package com.example.dhgatetest2.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.ui.BaseFragment;
import com.example.dhgatetest2.ui.BaseHolder;
import com.example.dhgatetest2.ui.MyBaseAdapter;

import java.util.List;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/22 22:22
 * @DESC: 搜索详情Fragment
 * @VERSION: V1.0
 */

public class SearchFragment extends BaseFragment {
    private List<String> mData;
    private MyBaseAdapter<String> mAdapter;


    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String newText = (String) arguments.get("newText");

        if (!TextUtils.isEmpty(newText)) {
            getDataFromServer(newText);
        }
    }

    //模拟从服务器获取数据
    private void getDataFromServer(final String newText) {
        ThreadUtils.runOnThread(new Runnable() {
            @Override
            public void run() {
                if (searchListener != null) {
                    if (searchListener.isSupportSearch()) {
                        mData = searchListener.search(newText);
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showData();
                            }
                        });
                    }
                }
            }
        });

    }

    //显示从服务器中获取的数据
    private void showData() {
        if (mAdapter == null) {
            mAdapter = new MyBaseAdapter<String>(mData) {
                @Override
                public BaseHolder<String> getBaseHolder() {
                    return new MyBaseHolder();
                }
            };
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    private class MyBaseHolder extends BaseHolder<String> {
        TextView textView;

        public MyBaseHolder() {
            super(mActivity, R.layout.item_list);
        }

        @Override
        public void initView(View mConvertView) {
            textView = (TextView) mConvertView.findViewById(R.id.tv_item);
        }

        @Override
        public void refreshView(int position, View convertView, final String item) {
            textView.setText(item);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    historyProvider.add(item);
                    if (searchListener != null) {
                        searchListener.click(item);
                    }
                }
            });
        }
    }

}
