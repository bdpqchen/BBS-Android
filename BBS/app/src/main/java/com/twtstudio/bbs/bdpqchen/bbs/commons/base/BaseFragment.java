package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.DaggerFragmentComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.FragmentComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.FragmentModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-4-21.
 * MVP-BaseFragment
 * provided Dagger injector
 * provided Presenter
 * provided Presenter
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView{

    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnbinder;

    protected boolean isInited = false;

    protected abstract int getFragmentLayoutId();
    protected abstract void injectFragment();
    protected abstract void initFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getFragmentLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        mUnbinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            if (!isHidden()) {
                isInited = true;
            }
        } else {

        }

    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
//        mPresenter = new XXXPresenter();
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }

    }

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }



}
