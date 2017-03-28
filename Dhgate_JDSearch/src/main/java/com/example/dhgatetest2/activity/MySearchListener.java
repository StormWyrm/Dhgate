package com.example.dhgatetest2.activity;

import com.example.dhgatetest2.MyApplication;
import com.example.dhgatetest2.framework.SearchListener;
import com.example.dhgatetest2.search.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/22 22:00
 * @DESC: $TODO
 * @VERSION: V1.0
 */
public class MySearchListener implements SearchListener {
    @Override
    public List<String> search(String value) {
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 6; i ++){
            data.add(value+i);
        }
        return data;
    }

    @Override
    public void click(String value, int type) {
        switch (type){
            case SearchListener.SEARCH_FORM_HISTORY:
                ToastUtils.showToast(MyApplication.mContext,"历史搜索: "+value);
                break;
            case SearchListener.SEARCH_FROM_HOT:
                ToastUtils.showToast(MyApplication.mContext,"热词搜索: "+value);
                break;
        }

    }

    @Override
    public boolean isSupportSearch() {
        return true;
    }
}
