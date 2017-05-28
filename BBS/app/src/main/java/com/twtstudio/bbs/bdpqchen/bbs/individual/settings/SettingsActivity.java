package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bdpqchen on 17-5-5.
 */

public class SettingsActivity extends BaseActivity {

    private static final String IS_SWITCH_NIGHT_MODE_LOCK = "isSwitchNightModeLock";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    @BindView(R.id.switch_no_network_message)
    SwitchCompat mSwitchNoNetworkMessage;
    @BindView(R.id.switch_stranger_message)
    SwitchCompat mSwitchStrangerMessage;
    @BindView(R.id.switch_night_mode)
    SwitchCompat mSwitchNightMode;
    @BindView(R.id.switch_auto_night_mode)
    SwitchCompat mSwitchAutoNightMode;
    @BindView(R.id.switch_slide_back)
    SwitchCompat mSwitchSlideBack;

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
        if (getIntent().getBooleanExtra(IS_SWITCH_NIGHT_MODE_LOCK, false)){
            mSlideBackLayout.lock(true);
        }else{
            mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        }
        mActivity = this;

        mSwitchNightMode.setChecked(PrefUtil.isNightMode());
        mSwitchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefUtil.setIsNightMode(isChecked);
                startMySelf();
            }
        });
/*
        mSwitchAutoNightMode.setChecked(PrefUtil.isAutoNightMode());
        mSwitchAutoNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefUtil.setIsAutoNightMode(isChecked);
            }
        });
*/
        mSwitchSlideBack.setChecked(PrefUtil.isSlideBackMode());
        mSwitchSlideBack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefUtil.setIsSlideBackMode(isChecked);
                mSlideBackLayout.lock(!isChecked);
            }
        });


    }


    public void startMySelf() {
        //方案1
        //要延迟更新，否则会很很卡顿，而且不能新开线程。暂行方案
        HandlerUtil.postDelay(() -> ActivityManager.getActivityManager().recreateAllActivity(SettingsActivity.class), 100);

        ActivityManager.getActivityManager().finishActivity(this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Intent intent = getIntent();
        intent.putExtra(IS_SWITCH_NIGHT_MODE_LOCK, true);
        startActivity(intent);
    }

    @OnClick({R.id.switch_no_network_message, R.id.switch_stranger_message, R.id.switch_night_mode, R.id.switch_auto_night_mode, R.id.switch_slide_back, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_no_network_message:
                break;
            case R.id.switch_stranger_message:
                break;
            case R.id.switch_night_mode:
                break;
            case R.id.switch_auto_night_mode:
                break;
            case R.id.switch_slide_back:
                break;
            case R.id.tv_logout:
                PrefUtil.setHadLogin(false);
                PrefUtil.setAuthToken("");
                PrefUtil.setAuthUsername("");
                PrefUtil.setAuthGroup(0);
                PrefUtil.setAuthUid(0);
                PrefUtil.setInfoNickname("");
                PrefUtil.setInfoSignature("");
                PrefUtil.setInfoCreate(0);
                PrefUtil.setInfoPoints(0);
                PrefUtil.setInfoUnread(0);
                PrefUtil.setHasUnSyncInfo(false);
                // TODO: 17-5-6 清除一些数据
                startActivity(new Intent(this, LoginActivity.class));
                finishMe();
                break;
        }
    }

}
