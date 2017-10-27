package com.twtstudio.bbs.bdpqchen.bbs.people;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-7-3.
 */

public class PeoplePresenter extends RxPresenter implements PeopleContract.Presenter {
    private PeopleContract.View mView;
    PeoplePresenter(PeopleContract.View view) {
        mView = view;
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
        addSubscribe(sHttpClient.getUserInfo(uid)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));

    }

    @Override
    public void addFriend(int uid, String confirmMsg) {
        ResponseTransformer<BaseModel> transformer = new ResponseTransformer<>();
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.onAddFriendFailed(msg);
                }
            }

            @Override
            public void _onNext(BaseModel model) {
                if (mView != null) {
                    mView.onAddFriend(model);
                }
            }
        };
        addSubscribe(sHttpClient.addFriend(uid, confirmMsg)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }
}
