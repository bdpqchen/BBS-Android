package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.BaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Arsener on 2017/5/28.
 */

public class MyReplyPresenter extends RxPresenter<MyReplyContract.View> implements MyReplyContract.Presenter {

    private int page;
    private boolean ready = true;
    private RxDoHttpClient<BoardsModel> mHttpClient;


    @Inject
    MyReplyPresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;
    }

    @Override
    public void initMyReplyList() {
        if (ready) {
            ready = false;
            page = 0;
            addSubscribe(mHttpClient.getMyReplyList(page)
                    .map(BaseResponse::getData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bean -> {
                        if (mView != null) {
                            mView.clearMyReplyList();
                            mView.showMyReplyList(bean);
                        }
                        ready = true;
                    }, throwable -> {
                        ready = true;
                    }));
            page++;
        }
    }

    @Override
    public void getMyReplyList() {
        if (ready) {
            ready = false;
            addSubscribe(mHttpClient.getMyReplyList(page)
                    .map(BaseResponse::getData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bean -> {
                        if (mView != null) {
                            mView.showMyReplyList(bean);
                        }
                        ready = true;
                    }, throwable -> {
                        ready = true;
                    }));
            page++;
        }
    }
}