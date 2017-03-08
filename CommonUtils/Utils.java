package com.framework.ui.keithly.uiframework.utils;

import android.content.res.Resources;

import com.framework.ui.keithly.uiframework.global.AppApplication;

/**
 * 常用的通用工具类
 * 包括:
 * 1.主线程更新UI
 * 2.获取资源管理类
 * 3.代码中获取dip值
 * 4.代码汇中获取颜色值
 * 5.解析xml中字符串数组属性
 */
public class Utils {
    /**
     * 在没有上下文的环境下,实现主线程中更新UI界面
     *
     * @param runnable Runnable对象
     */
    public static void runOnUIThread(Runnable runnable) {
        AppApplication.mainHandler.post(runnable);
    }

    /**
     * 获取资源管理的类
     *
     * @return 返回Resources对象
     */
    public static Resources getResources() {
        return AppApplication.context.getResources();
    }

    /**
     * 屏幕适配时,实现代码中使用dip属性值
     *
     * @param resId 传入资源ID
     * @return 返回Int类型dip值
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取颜色值
     *
     * @param resId 传入资源ID
     * @return 返回Int颜色值
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取字符串数组的信息
     * 用于解析arrays.xml中<string-array></string-array>信息
     *
     * @param resId 传入资源ID
     * @return 返回String数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }
}
