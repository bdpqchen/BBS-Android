package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-12.
 */

class ThreadPresenter extends RxPresenter<ThreadContract.View> implements ThreadContract.Presenter{

    private RxDoHttpClient<ThreadListModel> mHttpClient;

    @Inject
    ThreadPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }



}
