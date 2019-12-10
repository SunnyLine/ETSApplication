package com.typ.ets.jpush;

import android.content.Context;

import com.pullein.common.android.AppUtil;
import com.pullein.common.utils.Log;
import com.typ.ets.BuildConfig;
import com.typ.ets.MainActivity;
import com.typ.ets.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

import static com.typ.ets.Constant.KEY_PUSH_SHOW_NATIVE_TITLE;
import static com.typ.ets.Constant.KEY_PUSH_TYPE;
import static com.typ.ets.Constant.KEY_PUSH_URL;

/**
 * ETSApplication<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/6
 */
public class JReceiver extends JPushMessageReceiver {

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        String jsonStr = notificationMessage.notificationExtras;
        Log.d("onNotifyMessageOpened 用户点击了通知 data = " + notificationMessage.toString());

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            int pushType = jsonObject.optInt(KEY_PUSH_TYPE, 0);
            String url = jsonObject.optString(KEY_PUSH_URL, MainActivity.MAIN_URL);
            boolean showNativeTitle = jsonObject.optBoolean(KEY_PUSH_SHOW_NATIVE_TITLE, false);
            switch (pushType) {
                case 1:
                    WebViewActivity.start(context, url, showNativeTitle, null);
                    break;
                default:
                    AppUtil.startApp(context, BuildConfig.APPLICATION_ID);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
