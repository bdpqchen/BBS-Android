package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.DaggerFragmentComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.FragmentComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.FragmentModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by bdpqchen on 17-4-21.
 * MVP-BaseFragment
 * provided Dagger injector
 * provided Presenter
 * provided Presenter
 */

public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements BaseView {

    @Inject
    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected final String TAG = "LazyLoadFragment";
    private View view;

    protected boolean isInited = false;

    protected abstract int getFragmentLayoutId();
    protected abstract void injectFragment();
    protected abstract void initFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getFragmentLayoutId(), container, false);
        injectFragment();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);

        if (mPresenter != null){
            mPresenter.attachView(this);
        }else{
            LogUtil.d("mPresenter is null!!!");
        }
        if (savedInstanceState == null) {
            if (!isHidden()) {
                isInited = true;
            }
        } else {
            // TODO: 17-5-4 if (!isSupportHidden()) {
            onLazyInitView(savedInstanceState);
            isInited = true;
        }
        initFragment();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        initFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null){
            mUnBinder.unbind();
        }
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
