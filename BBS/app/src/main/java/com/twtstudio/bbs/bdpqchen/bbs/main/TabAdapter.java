package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.main.TopTen.TopTenFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.historyHot.HistoryHotFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostFragment;

import javax.inject.Inject;

/**
 * Created by zhangyulong on 5/12/17.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 3;

    private Context mContext;



    @Inject
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LatestPostFragment();
            case 1:
                return new LatestPostFragment();
            case 2:
                return new LatestPostFragment();
            default:
                return new LatestPostFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "最新动态";
            case 1:
                return "全站十大";
            case 2:
                return "历史热门";

        }
        return null;
    }
}
