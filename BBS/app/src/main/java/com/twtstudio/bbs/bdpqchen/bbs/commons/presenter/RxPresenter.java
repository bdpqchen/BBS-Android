package com.twtstudio.bbs.bdpqchen.bbs.commons.presenter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bdpqchen on 17-4-21.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable disposable){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void attachView(T view) {
        if (view != null){
            this.mView = view;
        }
    }

    @Override
    public void detachView() {
        if (mView != null){
            mView = null;
        }
        unSubscribe();
    }
}
