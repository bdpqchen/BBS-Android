package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by bdpqchen on 17-5-20.
 */

class ThreadListPresenter extends RxPresenter implements ThreadListContract.Presenter {

    private ThreadListContract.View mView;

    ThreadListPresenter(ThreadListContract.View view) {
        mView = view;
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
        addSubscribe(sHttpClient.getThreadList(boardId, page)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }
}
