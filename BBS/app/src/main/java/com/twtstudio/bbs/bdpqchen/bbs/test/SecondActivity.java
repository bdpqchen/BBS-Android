package com.twtstudio.bbs.bdpqchen.bbs.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.library.viewspread.helper.BaseViewHelper;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtils;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtils;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-4-19.
 */

public class SecondActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sc_night_mode)
    SwitchCompat scNightMode;

    private BaseViewHelper helper;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_second;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("第二");
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Slidr.attach(this);
//        StatusBarUtil.setColorForSwipeBack(this, ResourceUtils.getColor(this, R.color.colorAccent), 38);

        if (PrefUtils.isNightMode()) {
            helper = new BaseViewHelper.Builder(this)
                    .isFullWindow(true)
                    .isShowTransition(false)
                    .setDimColor(ResourceUtils.getColor(this, R.color.nightModeDim))
                    .setDimAlpha(200)
                    .create();
        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

