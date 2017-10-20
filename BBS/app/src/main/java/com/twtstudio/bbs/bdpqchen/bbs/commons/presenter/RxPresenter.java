package com.twtstudio.bbs.bdpqchen.bbs.commons.presenter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by bdpqchen on 17-4-21.
 */

public class RxPresenter implements BasePresenter {

    /*
    * 在每个 Presenter 里都会创建这个 CompositeDisposable,
    * so, unSubscribe 的调用时机为 each onDestroy() of Activity or Fragment be called.
    * */
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
