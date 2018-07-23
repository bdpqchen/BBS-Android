package com.twtstudio.bbs.bdpqchen.bbs.individual.release;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish.PublishPresenter;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-9-18.
 */

public class ReleaseActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tl_release)
    TabLayout mTlRelease;
    @BindView(R.id.vp_release)
    ViewPager mVpRelease;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_release;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("");
        return mToolbar;
    }

    @Override
    protected PublishPresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, Color.WHITE,0);
        enableLightStatusBarMode(true);
        ReleaseAdapter adapter = new ReleaseAdapter(getSupportFragmentManager());
        mVpRelease.setAdapter(adapter);
        mTlRelease.setupWithViewPager(mVpRelease);

    }
}
