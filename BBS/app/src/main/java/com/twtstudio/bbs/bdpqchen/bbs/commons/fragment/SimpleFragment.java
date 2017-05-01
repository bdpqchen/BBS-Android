package com.twtstudio.bbs.bdpqchen.bbs.commons.fragment;


import android.app.Activity;
import android.content.Context;
import android.view.View;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by bdpqchen on 17-5-1.
 * 提供一个无MVP架构的BaseFragment, 作为有MVP的BaseFragment的容器
 */

public class SimpleFragment extends SupportFragment{

    protected Context mContext;
    protected View mView;
    protected Activity mActivity;

    private Unbinder mUnbinder;
    private boolean isInited = false;


    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }





}
