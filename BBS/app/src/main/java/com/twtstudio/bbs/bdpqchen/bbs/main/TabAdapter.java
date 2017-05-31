package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.topTen.TopTenFragment;

import javax.inject.Inject;

/**
 * Created by zhangyulong on 5/12/17.
 */

public class TabAdapter extends FragmentPagerAdapter {
//    private static final int PAGE_COUNT = 2;
    private Context mContext;
    private static String[] titles = {"最新动态", "全站十大"};
    private BaseFragment[] fragments = new BaseFragment[2];


    @Inject
    public TabAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = LatestPostFragment.newInstance();
        fragments[1] = TopTenFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
