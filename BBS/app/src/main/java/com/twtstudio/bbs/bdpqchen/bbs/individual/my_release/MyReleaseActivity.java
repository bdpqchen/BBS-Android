package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Arsener on 2017/5/13.
 */

public class MyReleaseActivity extends BaseActivity<MyReleasePresenter>{

    @BindView(R.id.tl_release)
    TabLayout mTabLayout;
    @BindView(R.id.vp_release)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_release;
    }

    @Override
    protected Toolbar getToolbarView() {
        return null;
    }

    @Override
    protected boolean isShowBackArrow() {
        return false;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyReleaseFragmentAdapter myReleaseFragmentAdapter = new MyReleaseFragmentAdapter(getSupportFragmentManager());

        if (toolbar != null){
            toolbar.setTitle("我的发布");
        }
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mViewpager.setAdapter(myReleaseFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
    }


}
