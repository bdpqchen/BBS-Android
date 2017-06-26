package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bdpqchen on 17-5-20.
 */

class ThreadListPresenter extends RxPresenter<ThreadListContract.View> implements ThreadListContract.Presenter {

    private RxDoHttpClient<ThreadListModel> mHttpClient;
    private ResponseTransformer<ThreadListModel> mTransformer = new ResponseTransformer<>();

    @Inject
    ThreadListPresenter(RxDoHttpClient client) {
        this.mHttpClient = client;

    }

    @Override
    public void getThreadList(int boardId, int page) {

        SimpleObserver<ThreadListModel> observer = new SimpleObserver<ThreadListModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.showErrorMessage(msg);
            }

            @Override
            public void _onNext(ThreadListModel threadListModels) {
                if (mView != null)
                    mView.setThreadList(threadListModels);
            }
        };
        addSubscribe(mHttpClient.getThreadList(boardId, page)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }
}
