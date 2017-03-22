package com.example.dhgatetest2.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.base.BaseFragment;
import com.example.dhgatetest2.base.BaseHolder;
import com.example.dhgatetest2.base.MyBaseAdapter;
import com.example.dhgatetest2.util.ThreadUtils;
import com.example.dhgatetest2.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqingfeng on 2017/3/21.
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

    /**
     * 模拟从服务器获取数据，并解析
     *
     * @param newText
     */
    private void getDataFromServer(final String newText) {
        ThreadUtils.runOnThread(new Runnable() {
            @Override
            public void run() {
//                mData = new ArrayList<>();
//                mData.clear();
//                for (int i = 0; i < 6; i++) {
//                     mData.add(newText + i);
//                }
//
                if(searchListener != null){
                    if(searchListener.isSupportSearch()){
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

    /**
     * 显示请求数据
     */
    private void showData() {
        mAdapter = new MyBaseAdapter<String>(mData) {
            @Override
            public BaseHolder<String> getBaseHolder() {
                return new BaseHolder<String>(mActivity, R.layout.item_list) {
                    TextView textView;

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
                                ToastUtils.showToast(mActivity, "搜索：" + item);
                                historyProvider.add(item);

                                if (searchListener!= null){
                                    searchListener.click(item);
                                }
                            }
                        });

                    }
                };
            }
        };
        mListView.setAdapter(mAdapter);
    }


}
