package com.typ.ets;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pullein.common.android.web.BaseWebFragment;

import java.io.Serializable;
import java.util.HashMap;

import static com.typ.ets.Constant.KEY_DATA;
import static com.typ.ets.Constant.KEY_HEAD;
import static com.typ.ets.Constant.KEY_IS_SHOW_TOOLBAR;
import static com.typ.ets.MainActivity.MAIN_URL;

/**
 * ETSApplication<br>
 * describe ：
 *
 * @author xugang
 * @date 2019/9/6
 */
public class WebViewFragment extends BaseWebFragment implements View.OnClickListener {

    private ConstraintLayout mErrorLayout;
    private Toolbar mToolBar;
    private boolean isShowTitle;
    private String baseUrl;
    private HashMap<String, String> headMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            baseUrl = bundle.getString(KEY_DATA, MAIN_URL);
            isShowTitle = bundle.getBoolean(KEY_IS_SHOW_TOOLBAR, false);
            Serializable serializable = bundle.getSerializable(KEY_HEAD);
            if (serializable != null) {
                headMap = (HashMap<String, String>) serializable;
            }
        } else {
            baseUrl = MAIN_URL;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolBar = view.findViewById(R.id.toolbar);
        mErrorLayout = view.findViewById(R.id.conErrorView);
        initTitle();
        view.findViewById(R.id.tvReload).setOnClickListener(this);
        view.findViewById(R.id.tvClose).setOnClickListener(this);
        initWebView(view, R.id.webView);
        loadUrl(baseUrl, headMap);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web_close, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.close) {
            closeActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTitle() {
        if (!isShowTitle) {
            mToolBar.setVisibility(View.GONE);
            return;
        }
        setHasOptionsMenu(true);
        mToolBar.setVisibility(View.VISIBLE);
        mToolBar.setNavigationIcon(R.mipmap.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onBackPressed()) {
                    closeActivity();
                }
            }
        });
        mToolBar.inflateMenu(R.menu.menu_web_close);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.close) {
                    closeActivity();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setWebTitle(String title) {
        super.setWebTitle(title);
        boolean canEditTitle = getContext() != null && mToolBar != null && mToolBar.isShown() && !TextUtils.isEmpty(title);
        if (canEditTitle) {
            mToolBar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            String showTitle = title;
            if (title.length() > 5) {
                showTitle = title.substring(0, 5);
            }
            mToolBar.setTitle(showTitle);
        }
    }

    @Override
    public void hideErrorView() {
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        stopLoading();
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReload:
                reload();
                break;
            case R.id.tvClose:
                closeActivity();
                break;
            default:
                break;
        }
    }
}
