package com.twtstudio.bbs.bdpqchen.bbs.welcome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.R;

/**
 * Created by bdpqchen on 17-5-30.
 */

public class IntroPagerAdapter extends FragmentStatePagerAdapter {

    public IntroPagerAdapter(FragmentManager fm) {
        super(fm);


    }

    @Override
    public Fragment getItem(int position) {

        return IntroFragment.newInstance(getFragment(position));
    }

    private int getFragment(int position) {
        switch (position){
            case 0:
                return R.layout.fragment_intro_1;
            case 1:
                return R.layout.fragment_intro_2;
            case 2:
                return R.layout.fragment_intro_3;
        }
        return R.layout.fragment_intro_1;
    }

    @Override
    public int getCount() {
        return 3;
    }


/*
    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }
*/
}
