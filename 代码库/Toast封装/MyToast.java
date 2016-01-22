package com.miaotech.health.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/12/24.
 */
public class MyToast {
    public static Toast toast = null;

    public static void showToastShort(Context mContext,String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
    public static void showToastLong(Context mContext,String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
