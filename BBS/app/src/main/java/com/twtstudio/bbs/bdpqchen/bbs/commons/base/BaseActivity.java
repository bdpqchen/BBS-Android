package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.transition.Slide;

import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;
import com.oubowu.slideback.widget.SlideBackLayout;
import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtils;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtils;

/**
 * Created by bdpqchen on 17-4-18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private SlideBackLayout mSlideBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PrefUtils.isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            LogUtils.d("is mode night yes");
        }else{
            LogUtils.d("is mode night no");

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        /*mSlideBackLayout = SlideBackHelper.attach(
                // 当前Activity
                this,
                // Activity栈管理工具
                App.getActivityHelper(),
                // 参数的配置
                new SlideConfig.Builder()
                        // 屏幕是否旋转
                        .rotateScreen(true)
                        // 是否侧滑
                        .edgeOnly(false)
                        // 是否禁止侧滑
                        .lock(false)
                        // 边缘滑动的响应阈值，0~1，对应屏幕宽度*percent
                        .edgePercent(0.1f)
                        // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                        .slideOutPercent(0.5f).create(),
                // 滑动的监听
                null);*/

    }
}
