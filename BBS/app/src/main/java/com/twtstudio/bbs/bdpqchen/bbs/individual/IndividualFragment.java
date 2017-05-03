package com.twtstudio.bbs.bdpqchen.bbs.individual;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;

/**
 * Created by bdpqchen on 17-5-3.
 */

public class IndividualFragment extends BaseFragment<IndividualPresenter> implements IndividualContract.View {


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_individual;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {



    }




}
