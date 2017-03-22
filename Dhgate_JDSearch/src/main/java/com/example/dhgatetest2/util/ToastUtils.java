package com.example.dhgatetest2.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @AUTHER:       李青峰
 * @EMAIL:        1021690791@qq.com
 * @PHONE:        18045142956
 * @DATE:         2017/3/22 22:24
 * @DESC:         Toast的工具类
 * @VERSION:      V1.0
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToastSafe(final Context context, final String text) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, text);
            }
        });
    }

    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
