package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

import android.content.Context;
import android.text.AndroidCharacter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxBaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxBaseSubscriber;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;

import java.util.List;

import javax.inject.Inject;
import javax.xml.transform.Transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bdpqchen on 17-5-2.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> {

    private RxDoHttpClient mHttpClient;
    private Context mContext;

    @Inject
    public LoginPresenter(RxDoHttpClient client){
        mHttpClient = client;

    }

    public void doLogin(String username, String password){

        ObservableTransformer schedulers = new ObservableTransformer() {
            @Override
            public ObservableSource apply(@NonNull Observable observable) {
                return observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add((Disposable) mHttpClient.doLogin(username, password)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxBaseSubscriber<LoginModel>() {

                    @Override
                    public void _onError(String msg) {
                        LogUtil.d("_onError", msg);
                    }

                    @Override
                    public void _onNext(LoginModel loginModel) {
                        LogUtil.d("_onNext()", loginModel);
                    }

                }));

//        compositeDisposable.dispose();
    }

}
