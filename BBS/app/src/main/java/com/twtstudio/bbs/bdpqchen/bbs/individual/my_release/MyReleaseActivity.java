package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        toolbar.setTitle("我的发布");
        return toolbar;
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
        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        ButterKnife.bind(this);

        MyReleaseFragmentAdapter myReleaseFragmentAdapter = new MyReleaseFragmentAdapter(getSupportFragmentManager());

        mViewpager.setAdapter(myReleaseFragmentAdapter);
        mViewpager.setOffscreenPageLimit(2);
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


//        initData();
//
//        mViewpager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return mList.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
////            @Override
////            public Fragment getItem(int position) {
////                switch (position) {
////                    case 0:
////                        return MyReleaseFragment.newInstance();
////                    case 1:
////                        return MyReleaseFragment.newInstance();
//////            case 2:
//////                return HistoryHotFragment.newInstance();
////                    default:
////                        return null;
////                }
////            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                View view = mList.get(position);
//                container.addView(view);
//                return view;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView((View) object);
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return mTitle[position];
//            }
//        });

//    private void initData() {
////        MyReleaseFragment myReleaseFragmenta = new MyReleaseFragment();
////        MyReleaseFragment myReleaseFragmentb = new MyReleaseFragment();
//        View viewpagerA = getLayoutInflater().inflate(R.layout.fragment_release, null);
//        View viewpagerB = getLayoutInflater().inflate(R.layout.fragment_release, null);
//
//        mList = new ArrayList<>();
//        mList.add(viewpagerA);
//        mList.add(viewpagerB);
//
//        mTitle = new String[]{"首页", "分类"};
//    }