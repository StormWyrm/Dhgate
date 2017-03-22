package com.example.dhgatetest2.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.base.BaseFragment;
import com.example.dhgatetest2.base.BaseHolder;
import com.example.dhgatetest2.base.MyBaseAdapter;
import com.example.dhgatetest2.util.ToastUtils;

import java.util.List;

/**
 * Created by liqingfeng on 2017/3/21.
 */

public class HistoryFragment extends BaseFragment {
    private List<String> datas;
    private MyBaseAdapter<String> mAdapter;

    @Override
    protected void initData() {
        datas = historyProvider.getAll();
        showData();
    }

    /***
     * 显示搜索历史
     */
    private void showData() {
        if (datas != null && datas.size() != 0) {
            mTextView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);

            mAdapter = new MyBaseAdapter<String>(datas) {
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
                                    ToastUtils.showToast(mActivity,"搜索："+item);
                                }
                            });
                            textView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    showDeleteDialog(item);
                                    return false;
                                }
                            });
                        }
                    };
                }
            };
            mListView.setAdapter(mAdapter);
        } else {
            mTextView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
    }

    /***
     * 显示是否删除对话框
     */
    private void showDeleteDialog(final String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("确定要删除该搜索历史?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                historyProvider.delete(value);
                mAdapter.deleteData(value);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
