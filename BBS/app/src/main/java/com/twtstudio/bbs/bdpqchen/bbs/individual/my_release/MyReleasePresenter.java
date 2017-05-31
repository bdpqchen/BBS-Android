package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;


import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.BaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Arsener on 2017/5/13.
 */

public class MyReleasePresenter extends RxPresenter<MyReleaseContract.View> implements MyReleaseContract.Presenter {

    private int page;
    private boolean ready = true;
    private RxDoHttpClient<BoardsModel> mHttpClient;


    @Inject
    MyReleasePresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;
    }

    @Override
    public void initMyReleaseList() {
        if (ready) {
            ready = false;
            page = 0;
            addSubscribe(mHttpClient.getMyReleaseList(page)
                    .map(BaseResponse::getData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bean -> {
                        if (mView != null) {
                            mView.clearMyReleaseList();
                            mView.showMyReleaseList(bean);
                        }
                        ready = true;
                    }, throwable -> {
                        ready = true;
                    }));
            page++;
        }
    }

    @Override
    public void getMyReleaseList() {
        if (ready) {
            ready = false;
            addSubscribe(mHttpClient.getMyReleaseList(page)
                    .map(BaseResponse::getData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bean -> {
                        if (mView != null) {
                            mView.showMyReleaseList(bean);
                        }
                        ready = true;
                    }, throwable -> {
                        ready = true;
                    }));
            page++;
        }
    }
}
