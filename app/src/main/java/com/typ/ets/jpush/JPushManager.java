package com.typ.ets.jpush;

import android.content.Context;

import com.typ.ets.BuildConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * ETSApplication<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/6
 */
public class JPushManager {
    public static void init(Context context) {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(context);
        JPushInterface.setPowerSaveMode(context, true);
        JPushInterface.setLatestNotificationNumber(context, 99);
    }

    public static void requestPermission(Context context) {
        try {
            JPushInterface.requestPermission(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
