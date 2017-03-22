package com.example.dhgatetest2.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by liqingfeng on 2017/3/22.
 */

public class ThreadUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 在子线程中执行任务
     * */
    public static void runOnThread(Runnable runnable){
        new Thread(runnable).start();
    }
    /**
     * 在主线程中执行任务
     * */
    public static void runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }
    /**
     * 在主线程中延迟执行任务
     * */
    public static void runOnUiThreadDelayed(Runnable runnable,long delayMillis){
        handler.postDelayed(runnable,delayMillis);
    }
}
