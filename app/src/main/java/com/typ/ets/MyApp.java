package com.typ.ets;

import android.app.Application;

import com.pullein.common.android.AppUtil;
import com.pullein.common.android.ApplicationHelper;
import com.pullein.common.utils.Log;
import com.typ.ets.jpush.JPushManager;

/**
 * ETSApplication<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/6
 */
public class MyApp extends Application {
    ApplicationHelper mApplicationHelper;
    @Override
    public void onCreate() {
        super.onCreate();

        if (AppUtil.isMainProcess(this)) {
            JPushManager.init(this);
            Log.setPrintEnable(BuildConfig.DEBUG);
            if (mApplicationHelper == null) {
                mApplicationHelper = new ApplicationHelper();
            }
            mApplicationHelper.registerActivityLifecycle(this);
        }
    }
}
