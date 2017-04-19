package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.AppActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by bdpqchen on 17-4-18.
 */

public abstract class BaseActivity extends AppCompatActivity {

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
