package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.test.SecondActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-5.
 */

public class SettingsActivity extends BaseActivity {

    private static final String IS_SWITCH_NIGHT_MODE = "isSwitchNightMode";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_settings_container_1)
    FrameLayout mFlSettingsContainer1;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    @BindView(R.id.btn_start_second)
    Button mBtnStartSecond;
    @BindView(R.id.switch_night_mode)
    SwitchCompat mSwitchNightMode;

    private Activity mActivity;

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
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected boolean isSupportNightMode() {
        return true;
    }

    @Override
    protected void inject() {
//        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //本Activity不支持滑动返回，当前使用的滑动返回库不太友好
        mSlideBackLayout.lock(getIntent().getBooleanExtra(IS_SWITCH_NIGHT_MODE, false));

        SettingFragment1 fragment1 = new SettingFragment1();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_settings_container_1, fragment1);
        fragmentTransaction.commit();
        mActivity = this;
        mBtnStartSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SecondActivity.class));
            }
        });
        mSwitchNightMode.setChecked(PrefUtil.isNightMode());
        mSwitchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefUtil.setIsNightMode(isChecked);
                startMySelf();
            }
        });

    }


    public void startMySelf() {
        //方案1
        HandlerUtil.postDelay(new Runnable() {
            @Override
            public void run() {
                ActivityManager.getActivityManager().recreateAllActivity(SettingsActivity.class);
//                mSlideBackLayout.lock(true);
            }
        }, 100);

//        mSlideBackLayout.lock(true);
        ActivityManager.getActivityManager().finishActivity(this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Intent intent = getIntent();
        intent.putExtra(IS_SWITCH_NIGHT_MODE, true);
        startActivity(intent);

    }

    @OnClick(R.id.tv_logout)
    public void onViewClicked() {

    }

}
