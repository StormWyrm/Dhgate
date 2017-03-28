package com.example.dhgatetest2.search;

import android.content.Context;
import android.text.TextUtils;

import com.example.dhgatetest2.search.util.SPUtils;
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
 * @DESC: 获取搜索历史
 * @VERSION: V1.0
 */
public class HistoryProvider {
    public static final String JSON_HISTORY = "json_history";
    private static HistoryProvider historyProvider;

    private LinkedList<String> mDatas;
    private Context mContext;
    private Gson gson;
    private int defaultCacheSize = 20;

    private HistoryProvider(Context context, int defaultCacheSize) {
        this.mDatas = new LinkedList<>();
        this.mContext = context.getApplicationContext();
        gson = new Gson();

        //初始化 mDatas
        String json = (String) SPUtils.get(mContext, JSON_HISTORY, "");
        List<String> list = gson.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
        if (list != null && list.size() != 0) {
            for (String st : list) {
                mDatas.add(st);
            }
        }

    }

    public static HistoryProvider getInstance(Context context) {
        if (historyProvider == null) {
            synchronized (HistoryProvider.class) {
                if (historyProvider == null) {
                    historyProvider = new HistoryProvider(context, 0);
                }
            }
        }
        return historyProvider;
    }

    /**
     * 初始化默认缓存历史记录
     * @param defaultCacheSize
     */
    public void initDefaultCacheSize(int defaultCacheSize) {
        this.defaultCacheSize = defaultCacheSize;
    }

    /**
     * 添加一条搜索历史
     *
     * @param value
     */
    public void add(String value) {
        if (mDatas.size() < defaultCacheSize) {
            if (mDatas.contains(value)) {
                mDatas.remove(value);
            }
            mDatas.addFirst(value);
        } else {
            if (mDatas.contains(value)) {
                mDatas.remove(value);
            } else {
                mDatas.removeLast();
            }
            mDatas.addFirst(value);
        }
        commitLocal();
    }

    /**
     * 删除一条搜索历史
     *
     * @param value
     */
    public void delete(String value) {
        if (mDatas.contains(value)) {
            mDatas.remove(value);
            commitLocal();
        }
    }


    /**
     * 清空搜索历史
     */
    public void clear() {
        mDatas.clear();
        commitLocal();
    }

    /**
     * 后去所有搜索历史
     *
     * @return
     */
    public List<String> getAll() {
        List<String> list = new ArrayList<>();
        String json = (String) SPUtils.get(mContext, JSON_HISTORY, "");
        if (!TextUtils.isEmpty(json)) {
            list = gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 更新本地数据
     */
    private void commitLocal() {
        String json = gson.toJson(mDatas, new TypeToken<List<String>>() {
        }.getType());

        System.out.println("json :" + json);
        SPUtils.put(mContext, JSON_HISTORY, json);

    }


}