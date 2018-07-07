package com.twtstudio.bbs.bdpqchen.bbs.commons.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by bdpqchen on 17-5-1.
 * 提供一个无MVP架构的BaseFragment, 作为有MVP的BaseFragment的容器
 */

public abstract class SimpleFragment extends SupportFragment {

    protected Context mContext;
    protected View mView;
    protected Activity mActivity;

    protected abstract int getPerMainFragmentLayoutId();
    protected abstract void initFragments();

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getPerMainFragmentLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        if (savedInstanceState == null) {
            if (!isHidden()) {
                mInitialized = true;
                initFragments();
            }
        } else {
            // TODO: 17-5-2 理解它的懒加载模式以及isSupportHidden为什么不暴露了
            LogUtil.i("savedInstanceState != null");
            if (isVisible()) {
//                onLazyInitView(savedInstanceState);
                mInitialized = true;
                initFragments();
            }
*/
/*
            if (!isSupportHidden()) {
                mInitialized = true;
            }
*//*

        }
*/
        initFragments();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
       /* if (!mInitialized && !hidden) {
            mInitialized = true;
            initFragments();
        }*/
    }


}
