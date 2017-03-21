package com.example.dhgatetest2.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.base.BaseFragment;
import com.example.dhgatetest2.base.BaseHolder;
import com.example.dhgatetest2.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by liqingfeng on 2017/3/21.
 */

public class HistoryFragment extends BaseFragment {
    private List<String> datas;
    @Override
    protected void initData() {
        datas = historyProvider.getAll();
        datas.add("你好呀");
        datas.add("你好呀1");
        datas.add("你好呀2");
        datas.add("你好呀3");
        mListView.setAdapter(new MyBaseAdapter<String>(datas) {
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
