package com.example.dhgatetest2.base;

import com.example.dhgatetest2.MyApplication;
import com.example.dhgatetest2.util.ToastUtils;

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
    public void click(String value) {
        ToastUtils.showToast(MyApplication.mContext,"搜索: "+value);
    }

    @Override
    public boolean isSupportSearch() {
        return true;
    }
}
