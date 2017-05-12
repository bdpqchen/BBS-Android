package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class MainFragment extends SimpleFragment {


    LatestPostFragment latestPostFragment;
    @BindView(R.id.main_tablayout)
    TabLayout mTablayout;
    @BindView(R.id.main_viewpager)
    ViewPager mViewpager;
    Unbinder unbinder;
    @Override
    protected int getPerMainFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initFragments() {
        TabAdapter tabAdapter= new TabAdapter(getFragmentManager());
        mViewpager.setAdapter(tabAdapter);
        mTablayout.setupWithViewPager(mViewpager);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);;
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
