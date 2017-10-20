package com.twtstudio.bbs.bdpqchen.bbs.commons.presenter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bdpqchen on 17-4-21.
 */

public class RxPresenter implements BasePresenter {

    private CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable disposable){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unSubscribe(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.dispose();
        }
    }

}
