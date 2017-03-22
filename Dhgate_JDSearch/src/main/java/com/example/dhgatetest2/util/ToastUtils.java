package com.example.dhgatetest2.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liqingfeng on 2017/3/22.
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
