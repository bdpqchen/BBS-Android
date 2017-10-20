package com.twtstudio.bbs.bdpqchen.bbs.individual.friend;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-6-29.
 */

class FriendPresenter extends RxPresenter implements FriendContract.Presenter {
    private FriendContract.View mView;

    FriendPresenter(FriendContract.View view) {
        mView = view;
    }

    @Override
    public void getFriendList() {
        SimpleObserver<List<FriendModel>> observer = new SimpleObserver<List<FriendModel>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGetFriendFailed(msg);
            }

            @Override
            public void _onNext(List<FriendModel> FriendModel) {
                if (mView != null)
                    mView.onGetFriendList(FriendModel);
            }
        };
        addSubscribe(sHttpClient.getFriendList()
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }


}
