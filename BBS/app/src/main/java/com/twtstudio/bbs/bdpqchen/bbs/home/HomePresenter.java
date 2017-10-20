package com.twtstudio.bbs.bdpqchen.bbs.home;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-4-21.
 */

public class HomePresenter extends RxPresenter implements HomeContract.Presenter {
    private HomeContract.View mView;

    HomePresenter(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void getUnreadMessageCount() {
        SimpleObserver<Integer> observer = new SimpleObserver<Integer>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.onGetMessageFailed(msg);
                }
            }

            @Override
            public void _onNext(Integer integer) {
                if (mView != null) {
                    mView.onGotMessageCount(integer);
                }
            }
        };
        addSubscribe(sHttpClient.getUnreadCount()
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

}
