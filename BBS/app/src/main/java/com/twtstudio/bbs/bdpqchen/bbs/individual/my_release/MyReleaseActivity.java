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

public class MyReleaseActivity extends BaseActivity<MyReleasePresenter> implements MyReleaseContract.View {

    @BindView(R.id.tl_release)
    TabLayout mTabLayout;
    @BindView(R.id.vp_release)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    private ArrayList<View> mList;
//    private String[] mTitle;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_release;
    }

    @Override
    protected Toolbar getToolbarView() {
//        toolbar.setTitle("我的发布");
//        return toolbar;
        return null;
    }

    @Override
    protected boolean isShowBackArrow() {
        return false;
    }

    @Override
    protected boolean isSupportNightMode() {
        return true;
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

        toolbar.setTitle("我的发布");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mViewpager.setAdapter(myReleaseFragmentAdapter);
//        mViewpager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void clearMyReleaseList() {
    }

    @Override
    public void showMyReleaseList(List<MyReleaseModel> data) {
    }
}
