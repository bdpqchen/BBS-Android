package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class MainFragment extends SimpleFragment {

    @Override
    protected int getPerMainFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initFragments() {

    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }*/
}
