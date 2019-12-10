package com.typ.ets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pullein.common.android.FragmentHelper;
import com.pullein.common.utils.CollectionUtil;

import java.io.Serializable;
import java.util.HashMap;

import static com.typ.ets.Constant.KEY_DATA;
import static com.typ.ets.Constant.KEY_HEAD;
import static com.typ.ets.Constant.KEY_IS_SHOW_TOOLBAR;

public class WebViewActivity extends AppCompatActivity {

    private WebViewFragment mFragment;

    public static void start(Context context, String url, boolean isShowToolBar, HashMap<String, String> map) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(KEY_DATA, url);
        intent.putExtra(KEY_IS_SHOW_TOOLBAR, isShowToolBar);
        if (!CollectionUtil.isEmpty(map)) {
            intent.putExtra(KEY_HEAD, map);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_DATA);
        boolean isShowTitle = intent.getBooleanExtra(KEY_IS_SHOW_TOOLBAR, true);
        Serializable serializable = intent.getSerializableExtra(KEY_HEAD);
        HashMap<String, String> hashMap = null;
        if (serializable != null) {
            hashMap = (HashMap<String, String>) serializable;
        }
        mFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATA, url);
        bundle.putBoolean(KEY_IS_SHOW_TOOLBAR, isShowTitle);
        if (!CollectionUtil.isEmpty(hashMap)) {
            bundle.putSerializable(KEY_HEAD, hashMap);
        }
        mFragment.setArguments(bundle);
        FragmentHelper.add(getSupportFragmentManager(), R.id.container, mFragment);
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
