package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.VersionUtil;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleActivity;

import org.piwik.sdk.Tracker;
import org.piwik.sdk.extra.CustomVariables;
import org.piwik.sdk.extra.TrackHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by bdpqchen on 17-4-18.
 */

public abstract class BaseActivity extends SupportActivity {

    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    private WindowManager wm ;
    private DisplayMetrics dm;
    private Float density ;
//    protected SlideBackLayout mSlideBackLayout;

    protected abstract int getLayoutResourceId();

    protected abstract Toolbar getToolbarView();

    //use to dispose subscribe mainly.
    protected abstract BasePresenter getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(PrefUtil.isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(getLayoutResourceId());
        fixApi21blackBlockOnBottom();
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        density = dm.density;
        mUnBinder = ButterKnife.bind(this);
        mActivity = this;
        mContext = this;
        setArrowBack(true);

//        mSlideBackLayout = SlideBackHelper.attach(this, App.getActivityHelper(), getSlideConfig(), null);
/*
        if (mBasePresenter != null) {
            mBasePresenter.attachView(this);
        } else {
            LogUtil.d("mBasePresenter is null!!!");
        }
*/

        StatusBarUtil.setColor(this, ResourceUtil.getColor(this, R.color.colorPrimaryDark), 0);
        ActivityManager.getActivityManager().addActivity(this);
    }

    private void setArrowBack(boolean available) {
        Toolbar toolbar = getToolbarView();
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(available);
            }
        }
    }
/*

    private SlideConfig getSlideConfig() {
        return new SlideConfig.Builder()
                .rotateScreen(true)
                .edgeOnly(true)
                .edgePercent(0.3f)
                .slideOutPercent(0.2f)
                .create();
    }
*/

    //由于滑动返回库的bug 目前只在5.0系统上出现此问题, 暂时修复方案
    private void fixApi21blackBlockOnBottom() {
        String className = getClass().getSimpleName();
        if (!(className.equals(PeopleActivity.class.getSimpleName())
                || className.equals(HomeActivity.class.getSimpleName())
                || className.equals(LoginActivity.class.getSimpleName()))) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                FrameLayout view = (FrameLayout) findViewById(android.R.id.content);
                if (view.getChildCount() > 0) view.getChildAt(0).setFitsSystemWindows(true);
                else view.setFitsSystemWindows(true);
            }
        }
    }

    protected Tracker getTracker() {
        return ((App) getApplication()).getTracker();
    }

    protected TrackHelper getTrackerHelper() {
        CustomVariables variables = new CustomVariables();
        variables.put(1, "Android Device", Build.MODEL);
        variables.put(2, "Android App Version", getResources().getString(R.string.version_name));
        variables.put(3, "Android OS Version", Build.VERSION.RELEASE);
        return TrackHelper.track(variables.toVisitVariables());
    }

    private void finishThisActivity() {
        ActivityManager.getActivityManager().finishActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().unSubscribe();
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    @Override
    public void onBackPressedSupport() {
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

    public void finishMe() {
        finishThisActivity();
    }

    public int getWidthInDp() {
        wm.getDefaultDisplay().getMetrics(dm);
        int widthPx = dm.widthPixels;
        Float res = (widthPx / density);
        return res.intValue();
    }

    public int getHeightInDp() {
        wm.getDefaultDisplay().getMetrics(dm);
        int heightPx = dm.heightPixels;
        Float res = heightPx / density;
        return res.intValue();
    }

    public String enableLightStatusBarMode(boolean enable){

        try{
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int darkModeFlag = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(layoutParams);
            Method extraFlagField = getWindow().getClass().getMethod("setExtraFlags",int.class,int.class);
            extraFlagField.invoke(getWindow(),enable? darkModeFlag : 0,darkModeFlag);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                getWindow().getDecorView().setSystemUiVisibility(enable? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_VISIBLE);
            }
            return "MIUI";
        } catch (Exception e){

        }

        try{
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(layoutParams);
            value = enable? value|bit : value& ~bit;
            meizuFlags.setInt(layoutParams,value);
            getWindow().setAttributes(layoutParams);
            return "Flyme";
        } catch (Exception e){

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(enable ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_VISIBLE);
            return "Android M";
        }


        return "None";

    }

}
