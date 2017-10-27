package com.twtstudio.bbs.bdpqchen.bbs.individual.release;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish.PublishFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply.ReplyFragment;

/**
 * Created by bdpqchen on 17-9-18.
 */

public class ReleaseAdapter extends FragmentPagerAdapter {
    private static String[] mTitles = {"发贴", "回帖"};

    ReleaseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PublishFragment.newInstance();
        } else {
            return ReplyFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
