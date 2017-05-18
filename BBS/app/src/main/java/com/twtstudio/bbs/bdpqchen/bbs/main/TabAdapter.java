package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.historyHot.HistoryHotFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostModel;

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
        BaseFragment showFragment=null;
        LatestPostFragment latestPostFragment=null;
        switch (position) {
            case 0: if(latestPostFragment==null) {
                latestPostFragment=new LatestPostFragment();
                showFragment=latestPostFragment;
            }
            case 1:
                if(latestPostFragment==null) {
                    latestPostFragment=new LatestPostFragment();
                    showFragment=latestPostFragment;
                };
            case 2:
                if(latestPostFragment==null) {
                    latestPostFragment=new LatestPostFragment();
                    showFragment=latestPostFragment;
                };
            default:
                if(latestPostFragment==null) {
                    latestPostFragment=new LatestPostFragment();
                    showFragment=latestPostFragment;
                };
                return showFragment;
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
