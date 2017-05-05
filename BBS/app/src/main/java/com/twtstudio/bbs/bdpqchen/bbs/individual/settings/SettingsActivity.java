package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.test.SecondActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-5.
 */

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_settings_container_1)
    FrameLayout mFlSettingsContainer1;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    @BindView(R.id.btn_start_second)
    Button mBtnStartSecond;

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

    }

    @OnClick(R.id.tv_logout)
    public void onViewClicked() {
//        !PrefUtil.isNightMode();
//        PrefUtil.setIsNightMode(true);
//        finish();
//        mActivity.recreate();
//        ActivityManager.getActivityManager().recreateAllActivity(SettingsActivity.class);
        LogUtil.d(PrefUtil.isNightMode());
        LogUtil.d("set night mode");


//        ActivityManager.getActivityManager().finishActivity(mActivity);
//        mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        mActivity.startActivity(mActivity.getIntent());

//        Activity activity = this;
//        activity.getWindow().setWindowAnimations(android.R.style.WindowAnimationFadeInOut);
//        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        activity.recreate();

    }

}
