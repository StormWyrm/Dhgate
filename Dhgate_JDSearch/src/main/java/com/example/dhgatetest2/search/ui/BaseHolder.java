package com.example.dhgatetest2.search.ui;

import android.content.Context;
import android.view.View;

/**
 * @AUTHER:       李青峰
 * @EMAIL:        1021690791@qq.com
 * @PHONE:        18045142956
 * @DATE:         2017/3/22 22:16
 * @DESC:         自定义Holder
 * @VERSION:      V1.0
 */
public abstract class BaseHolder<T> {
    private View mConvertView;

    public BaseHolder(Context context, int resourceId) {
        mConvertView = View.inflate(context, resourceId, null);
        initView(mConvertView);
        mConvertView.setTag(this);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 初始化Holder中的布局参数
     * @param mConvertView
     */
    public abstract void initView(View mConvertView);

    /**
     * 刷新数据
     * @param position view的位置
     * @param convertView 复用的的view
     * @param item 当前view位置的数据
     */
    public abstract void refreshView(int position, View convertView, T item);
}
