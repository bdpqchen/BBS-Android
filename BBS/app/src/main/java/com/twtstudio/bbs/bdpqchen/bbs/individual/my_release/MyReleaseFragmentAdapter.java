package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply.MyReplyFragment;

import javax.inject.Inject;

/**
 * Created by Arsener on 2017/5/28.
 */

public class MyReleaseFragmentAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;
    private Context mContext;
    private static String[] titles = {"我的发贴", "我的回帖"};


    @Inject
    public MyReleaseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyReleaseFragment.newInstance();
            case 1:
                return MyReplyFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
