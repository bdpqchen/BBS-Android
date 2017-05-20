package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Inject;


/**
 * Created by bdpqchen on 17-5-20.
 */

class ThreadListPresenter extends RxPresenter<ThreadListContract.View> implements ThreadListContract.Presenter{

    private RxDoHttpClient<ThreadListModel> mHttpClient;

    @Inject
    ThreadListPresenter(RxDoHttpClient client){
        this.mHttpClient = client;

    }

}
