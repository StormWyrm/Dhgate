package com.example.dhgatetest2.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/21 22:51
 * @DESC: 获取历史搜索历史
 * @VERSION: V1.0
 */
public class HistoryProvider {
    public static final String JSON_HISTORY = "json_history";

    private LinkedList<String> mDatas;
    private Context mContext;
    private Gson gson;

    public HistoryProvider(Context context) {
        this.mDatas = new LinkedList<>();
        this.mContext = context.getApplicationContext();
        gson = new Gson();
        init();
    }


    private void init() {
        String json = (String) SPUtils.get(mContext, JSON_HISTORY, "");
        List<String> list = gson.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
        if (list != null && list.size() != 0) {
            for (String st : list) {
                mDatas.add(st);
            }
        }
    }

    public void add(String value) {
        if (mDatas.contains(value)) {
            mDatas.remove(value);
        }
        mDatas.addFirst(value);
        commitLocal();
    }

    public void delete(String value) {
        if (mDatas.contains(value)) {
            mDatas.remove(value);
            commitLocal();
        }
    }


    public void clear() {
        mDatas.clear();
        commitLocal();
    }

    public List<String> getAll() {
        List<String> list = new ArrayList<>();
        String json = (String) SPUtils.get(mContext, JSON_HISTORY, "");
        if (!TextUtils.isEmpty(json)) {
            list = gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        }
        return list;
    }

    private void commitLocal() {
        String json = gson.toJson(mDatas, new TypeToken<List<String>>() {
        }.getType());

        System.out.println("json :" + json);
        SPUtils.put(mContext, JSON_HISTORY, json);

    }


}