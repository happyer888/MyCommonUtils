package com.keithly.demo.testdemo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast sToast;

    /**
     * Shows a toast message while be called.
     */
    public static void showToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }

    /**
     * Shows a toast message for a long time while be called.
     */
    public static void showLongToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
        }
        sToast.setText(msg);
        sToast.show();
    }
}