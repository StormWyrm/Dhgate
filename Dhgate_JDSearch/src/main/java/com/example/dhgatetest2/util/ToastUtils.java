package com.example.dhgatetest2.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liqingfeng on 2017/3/22.
 */

public class ToastUtils {
    public static void showToastSafe(final Context context, final String text) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
