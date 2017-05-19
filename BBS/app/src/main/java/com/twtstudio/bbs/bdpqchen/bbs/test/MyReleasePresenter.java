package com.twtstudio.bbs.bdpqchen.bbs.test;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.test.myReleaseModle.MyReleaseManager;

import javax.inject.Inject;

/**
 * Created by Arsener on 2017/5/13.
 */

public class MyReleasePresenter extends RxPresenter<MyReleaseContract.View> implements MyReleaseContract.Presenter{

    private MyReleaseContract.View myReleaseContract;

    private MyReleaseManager myReleaseManager;

    private int page;
    private boolean ready = true;
    private RxDoHttpClient<BoardsModel> mHttpClient;


    @Inject
    MyReleasePresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;

    }

    public MyReleasePresenter(MyReleaseContract.View mController) {
        this.myReleaseContract = mController;
        myReleaseManager = MyReleaseManager.getInstance();
    }

    public void initData() {
        if (ready) {
            ready = false;
            page = 1;
            myReleaseManager.getMyReleaseList(bean -> {
                        myReleaseContract.getMyReleaseList(bean);
                        ready = true;
                    },
                    throwable -> {
                        myReleaseContract.onError(throwable);
                        ready = true;
                    },
                    page);
            page++;
        }
    }

    public void getMoreData() {
        if (ready) {
            ready = false;
            myReleaseManager.getMyReleaseList(bean -> {
                        myReleaseContract.getMore(bean);
                        ready = true;
                    },
                    throwable -> {
                        myReleaseContract.onError(throwable);
                        ready = true;
                    },
                    page);
            page++;
        }
    }
}
