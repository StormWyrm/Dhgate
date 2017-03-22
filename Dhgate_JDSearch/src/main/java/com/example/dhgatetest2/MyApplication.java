package com.example.dhgatetest2;

import android.app.Application;
import android.content.Context;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/22 22:05
 * @DESC: $TODO
 * @VERSION: V1.0
 */
public class MyApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


}
