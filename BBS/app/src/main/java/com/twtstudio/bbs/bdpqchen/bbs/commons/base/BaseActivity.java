package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;
import com.oubowu.slideback.widget.SlideBackLayout;
import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.ActivityComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.DaggerActivityComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.ActivityModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by bdpqchen on 17-4-18.
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {

    @Inject
    protected T mPresenter;

    private Toolbar mToolbar;
    private Unbinder mUnBinder;
    public SlideBackLayout mSlideBackLayout;

    protected abstract int getLayoutResourceId();

    protected abstract Toolbar getToolbarView();

    protected abstract boolean isShowBackArrow();

    protected abstract boolean isSupportNightMode();

    protected abstract void inject();

    protected abstract Activity supportSlideBack();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isSupportNightMode()) {
            // TODO: 17-5-6 将权限检查写一个Util 或使用第三方库
            AppCompatDelegate.setDefaultNightMode(PrefUtil.isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
/*
            if (PrefUtil.isAutoNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            } else {
            }
*/
        }

        setContentView(getLayoutResourceId());

        mUnBinder = ButterKnife.bind(this);
        ActivityManager.getActivityManager().addActivity(this);

        StatusBarUtil.setColor(this, ResourceUtil.getColor(this, R.color.colorPrimary), 0);

        inject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        mToolbar = getToolbarView();
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            if (isShowBackArrow()) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {

                }
            }
        }

        Activity activity = supportSlideBack();
        if (activity != null) {
            // TODO: 17-4-26 one hand mode
            SlideConfig slideConfig = new SlideConfig.Builder()
                    .rotateScreen(false)
                    .edgeOnly(true)
                    .edgePercent(0.2f).slideOutPercent(0.3f).create();
            mSlideBackLayout = SlideBackHelper.attach(activity, App.getActivityHelper(), slideConfig, null);

        }

    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    private void finishThisActivity() {
        ActivityManager.getActivityManager().finishActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        mUnBinder.unbind();
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




}
