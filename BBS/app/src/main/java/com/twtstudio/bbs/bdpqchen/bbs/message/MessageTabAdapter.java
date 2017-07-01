package com.twtstudio.bbs.bdpqchen.bbs.message;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.message.personal.PersonalFragment;
import com.twtstudio.bbs.bdpqchen.bbs.message.system.SystemFragment;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-6-5.
 */

public class MessageTabAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private final String[] titles = {"系统", "私信"};

    @Inject
    public MessageTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SystemFragment.newInstance();
            case 1:
                return PersonalFragment.newInstance();
        }
        return null;
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
