package com.twtstudio.bbs.bdpqchen.bbs.people;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-7-3.
 */

public class PeoplePresenter extends RxPresenter<PeopleContract.View> implements PeopleContract.Presenter {

    RxDoHttpClient<PeopleModel> mHttpClient;

    @Inject
    PeoplePresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;
    }

    @Override
    public void getUserInfo(int uid) {
        SimpleObserver<PeopleModel> observer = new SimpleObserver<PeopleModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.onGetUserInfoFailed(msg);
                }
            }

            @Override
            public void _onNext(PeopleModel peopleModel) {
                if (mView != null) {
                    mView.onGetUserInfo(peopleModel);
                }
            }
        };
        addSubscribe(mHttpClient.getUserInfo(uid)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));

    }

}
