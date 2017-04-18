package com.twtstudio.bbs.bdpqchen.bbs.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.library.viewspread.helper.BaseViewHelper;
import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;
import com.oubowu.slideback.widget.SlideBackLayout;
import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtils;

/**
 * Created by bdpqchen on 17-4-19.
 */

public class SecondActivity extends BaseActivity {

    private BaseViewHelper helper;

    private SlideBackLayout mSlideBackLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefUtils.isNightMode()) {
            Log.i("Switch", "isnightMode");

            helper = new BaseViewHelper
                    .Builder(this)
                    .isFullWindow(true)
                    .isShowTransition(false)
                    .setDimColor(getResources().getColor(R.color.nightModeDim))
                    .setDimAlpha(200)
                    .create();
//            PrefUtils.setIsNightMode();
        }

        setContentView(R.layout.activity_second);

        mSlideBackLayout = SlideBackHelper.attach(
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
                null);

        final SwitchCompat switchNightMode = (SwitchCompat) findViewById(R.id.sc_night_mode);
        switchNightMode.setChecked(PrefUtils.isNightMode());
        switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PrefUtils.setIsNightMode(true);
                } else {
                    PrefUtils.setIsNightMode(false);
                }
                startMySelf(switchNightMode);
            }

        });
    }

    public void startMySelf(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        new BaseViewHelper
                .Builder(this, view)
                .startActivity(intent);
        finish();

    }

}

