package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class MainFragment extends SimpleFragment {

    @BindView(R.id.btn_log_out)
    Button mButtonLogout;

    @Override
    protected int getPerMainFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initFragments() {
        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefUtil.setHadLogin(false);
            }
        });
    }





}
