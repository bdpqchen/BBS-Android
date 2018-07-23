package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-5.
 */

public class SettingsActivity extends BaseActivity {

    public static final String IS_SWITCH_NIGHT_MODE_LOCK = "isSwitchNightModeLock";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settings;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("通用设置");
        return mToolbar;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, Color.WHITE,0);
        enableLightStatusBarMode(true);

        //本Activity不支持滑动返回，当前使用的滑动返回库不太友好
/*
        if (getIntent().getBooleanExtra(IS_SWITCH_NIGHT_MODE_LOCK, false)) {
            mSlideBackLayout.lock(true);
        } else {
            mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        }
*/
        SettingsFragment fragment = new SettingsFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.settings_container, fragment).commit();

    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finishThisActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishThisActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void finishThisActivity() {
        finishMe();
    }


}
