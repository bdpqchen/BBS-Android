package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class MainFragment extends SimpleFragment {


    LatestPostFragment latestPostFragment;
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mViewpager;
    @BindView(R.id.toolBar_main)
    Toolbar mToolbar;
    @BindView(R.id.iv_announce)
    ImageView mIvAnnounce;

    @Override
    protected int getPerMainFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    protected void initFragments() {
        mToolbar.setTitle("求实BBS");
        mIvAnnounce.setOnClickListener(v -> {
            SnackBarUtil.notice(this.getActivity(), "还没有公告");
        });
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        mViewpager.setAdapter(tabAdapter);
        mViewpager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewpager);

    }



}
