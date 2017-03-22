package com.example.dhgatetest2.base;

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

    public abstract void initView(View mConvertView);

    public abstract void refreshView(int position, View convertView, T item);
}
