package com.example.dhgatetest2.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.dhgatetest2.R;
import com.example.dhgatetest2.base.BaseFragment;
import com.example.dhgatetest2.base.BaseHolder;
import com.example.dhgatetest2.base.MyBaseAdapter;

import java.util.List;


/**
 * @AUTHER:       李青峰
 * @EMAIL:        1021690791@qq.com
 * @PHONE:        18045142956
 * @DATE:         2017/3/22 22:22
 * @DESC:         搜索历史Fragment
 * @VERSION:      V1.0
 */
public class HistoryFragment extends BaseFragment {
    private List<String> datas;
    private MyBaseAdapter<String> mAdapter;

    @Override
    protected void initData() {
        datas = historyProvider.getAll();
        showData();
    }

    //显示搜索历史数据
    private void showData() {
        if (datas != null && datas.size() != 0) {
            mTextView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            addFootAndHeadView();
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
//                                    ToastUtils.showToast(mActivity, "搜索：" + item);
                                    if (searchListener!= null){
                                        searchListener.click(item);
                                    }
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

    //删除搜索历史的对话框
    private void showDeleteDialog(final String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("确定要删除该搜索历史?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                historyProvider.delete(value);
                mAdapter.deleteData(value);
                if (mAdapter.getCount() == 0) {
                    mTextView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                } else {
                    mAdapter.notifyDataSetChanged();
                }

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

    //在ListView中添加头尾布局
    private void addFootAndHeadView() {
        View headerView = View.inflate(mActivity, R.layout.layout_header, null);
        mListView.addHeaderView(headerView);

        View footerView = View.inflate(mActivity, R.layout.layout_footer, null);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyProvider.clear();
                datas.clear();
                mTextView.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }
        });
        mListView.addFooterView(footerView);
    }
}
