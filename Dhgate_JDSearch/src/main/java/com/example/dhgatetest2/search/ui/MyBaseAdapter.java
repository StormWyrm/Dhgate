package com.example.dhgatetest2.search.ui;

import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/22 22:17
 * @DESC: Adapter的基类
 * @VERSION: V1.0
 */
public abstract class MyBaseAdapter<T> extends android.widget.BaseAdapter {
    private List<T> list;
    private BaseHolder holder;

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

    public void deleteData(T value) {
        if (list.contains(value)) {
            list.remove(value);
        }
    }

    public void addData(T value) {
        if (list.contains(value)) {
            list.remove(value);
            list.add(0, value);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = getBaseHolder();
            convertView = holder.getConvertView();
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        T item = getItem(position);
        holder.refreshView(position, convertView, item);
        return convertView;
    }

    public abstract BaseHolder<T> getBaseHolder();
}
