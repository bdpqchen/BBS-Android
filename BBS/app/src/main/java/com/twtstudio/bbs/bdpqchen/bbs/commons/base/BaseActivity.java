package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.oubowu.slideback.widget.SlideBackLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.AppActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtils;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtils;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by bdpqchen on 17-4-18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private SlideBackLayout mSlideBackLayout;
    private Toolbar mToolbar;
    private Unbinder mUnbinder;

    protected abstract int getLayoutResourceId();

    protected abstract Toolbar getToolbarView();

    protected abstract boolean isShowBackArrow();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(PrefUtils.isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(getLayoutResourceId());

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(ResourceUtils.getColor(this, R.color.colorPrimaryDark));
            LogUtils.d("sot the color");
//            tintManager.setStatusBarTintResource(ResourceUtils.getColor(this, R.color.colorAccent));
            tintManager.setNavigationBarTintColor(ResourceUtils.getColor(this, R.color.colorPrimaryDark));
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//        map.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
//        }

        mToolbar = getToolbarView();
        if (null != mToolbar){
            getSupportActionBar();
            if (isShowBackArrow()){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mUnbinder = ButterKnife.bind(this);
        AppActivityManager.getActivityManager().addActivity(this);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
