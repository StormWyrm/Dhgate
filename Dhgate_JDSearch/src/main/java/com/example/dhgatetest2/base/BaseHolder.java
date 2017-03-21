package com.example.dhgatetest2.base;

import android.content.Context;
import android.view.View;

/**
 * Created by 10216 on 2016/6/10.
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
