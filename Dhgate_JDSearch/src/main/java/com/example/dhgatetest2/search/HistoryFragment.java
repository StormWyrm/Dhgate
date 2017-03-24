package com.example.dhgatetest2.search;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
 * @DESC: 搜索历史Fragment
 * @VERSION: V1.0
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
                                    if (searchListener != null) {
                                        searchListener.click(item);
                                        historyProvider.add(item);
                                        mAdapter.addData(item);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                            textView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {

                                    showDeleteDialog("", new Runnable() {
                                        @Override
                                        public void run() {
                                            historyProvider.delete(item);
                                            mAdapter.deleteData(item);
                                            if (mAdapter.getCount() == 0) {
                                                mTextView.setVisibility(View.VISIBLE);
                                                mListView.setVisibility(View.GONE);
                                            } else {
                                                mAdapter.notifyDataSetChanged();
                                            }

                                        }
                                    });
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

    //显示对话框
    private void showDeleteDialog(final String title, final Runnable todo) {
        final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();

        View view = View.inflate(mActivity, R.layout.dialog_search_delete_history, null);
        final TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        final TextView tvConfirm = (TextView) view.findViewById(R.id.tv_dialog_confirm);
        TextView tvCancle = (TextView) view.findViewById(R.id.tv_dialog_cancle);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo.run();
                dialog.dismiss();
            }
        });

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);

        dialog.show();
    }

    //在ListView中添加头尾布局
    private void addFootAndHeadView() {
        View headerView = View.inflate(mActivity, R.layout.layout_header, null);
        mListView.addHeaderView(headerView);

        View footerView = View.inflate(mActivity, R.layout.layout_footer, null);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(getString(R.string.search_dialog_clear_title), new Runnable() {
                    @Override
                    public void run() {
                        historyProvider.clear();
                        datas.clear();
                        mTextView.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.GONE);
                    }
                });
            }
        });
        mListView.addFooterView(footerView);
    }
}
