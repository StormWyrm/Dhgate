package com.example.dhgatetest2.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 10216 on 2016/6/10.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private List<T> list;

    public MyBaseAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (list != null)
            return list.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            holder = getBaseHolder();
            convertView = holder.getConvertView();
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        T item = getItem(position);
        holder.refreshView(position,convertView,item);
        return convertView;
    }

    public abstract BaseHolder<T> getBaseHolder();
}
