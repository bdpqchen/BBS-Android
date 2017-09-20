package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class MainFragment extends SimpleFragment {
    public static final String ARG_PAGE = "arg_page";

    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mViewpager;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getPerMainFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initFragments() {
        mAppbar.setExpanded(false);
        StatusBarUtil.setColor(this.getActivity(), ResourceUtil.getColor(this.getActivity(), R.color.colorPrimaryDark), 0);
        MainTabAdapter tabAdapter = new MainTabAdapter(getFragmentManager(), mContext);
        mViewpager.setAdapter(tabAdapter);
        mTabLayout.setupWithViewPager(mViewpager);

    }



}
