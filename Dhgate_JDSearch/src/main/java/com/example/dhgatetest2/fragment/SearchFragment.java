package com.example.dhgatetest2.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.base.BaseFragment;
import com.example.dhgatetest2.base.BaseHolder;
import com.example.dhgatetest2.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqingfeng on 2017/3/21.
 */

public class SearchFragment extends BaseFragment {
    private List<String> mData;

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String newText = (String) arguments.get("newText");

        if (!TextUtils.isEmpty(newText)) {
            getDataFromServer(newText);
            mListView.setAdapter(new MyBaseAdapter<String>(mData) {
                @Override
                public BaseHolder<String> getBaseHolder() {
                    return new BaseHolder<String>(mActivity, R.layout.item_list) {
                        TextView textView;

                        @Override
                        public void initView(View mConvertView) {
                            textView = (TextView) mConvertView.findViewById(R.id.tv_item);
                        }

                        @Override
                        public void refreshView(int position, View convertView, String item) {
                            textView.setText(item);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(mActivity, textView.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    };
                }
            });

        }
    }

    private void getDataFromServer(String newText) {
        mData = new ArrayList<>();
        mData.clear();
        for (int i = 0; i < 6; i++) {
            mData.add(newText + i);
        }
    }
}
