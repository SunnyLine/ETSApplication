package com.typ.ets;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.pullein.common.android.FragmentHelper;
import com.pullein.common.utils.SPUtil;
import com.typ.ets.jpush.JPushManager;
import com.typ.ets.utils.NotificationsUtils;

import static com.typ.ets.Constant.KEY_DATA;
import static com.typ.ets.Constant.KEY_IS_REMIND;
import static com.typ.ets.Constant.KEY_IS_SHOW_TOOLBAR;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_URL = "https://ets2.cn";
    WebViewFragment mWebFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mWebFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATA, MAIN_URL);
        bundle.putBoolean(KEY_IS_SHOW_TOOLBAR, false);
        mWebFragment.setArguments(bundle);
        FragmentHelper.add(getSupportFragmentManager(), R.id.container, mWebFragment);

        checkNotifyPermission();

        JPushManager.requestPermission(this);
    }

    private void checkNotifyPermission() {
        if (SPUtil.getBoolean(this, KEY_IS_REMIND, false)) {
            return;
        }
        if (!NotificationsUtils.notificationEnabled(this)) {
            new AlertDialog.Builder(this)
                    .setMessage("打开通知权限，获取最新消息")
                    .setCancelable(false)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SPUtil.putBoolean(MainActivity.this, KEY_IS_REMIND, true);
                        }
                    }).setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NotificationsUtils.requestNotify(MainActivity.this);
                }
            }).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebFragment != null && mWebFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
