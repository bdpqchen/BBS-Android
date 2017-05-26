package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-12.
 */

class ThreadPresenter extends RxPresenter<ThreadContract.View> implements ThreadContract.Presenter{

    private RxDoHttpClient<ThreadModel> mHttpClient;

    @Inject
    ThreadPresenter(RxDoHttpClient client){
        mHttpClient = client;
    }


    @Override
    public void getThread(int threadId, int postPage) {
        SimpleObserver<ThreadModel> observer = new SimpleObserver<ThreadModel>() {
            @Override
            public void _onError(String msg) {
                mView.showFailed(msg);
            }

            @Override
            public void _onNext(ThreadModel model) {
                mView.showThread(model);
            }

        };
        addSubscribe(mHttpClient.getThread(threadId, postPage)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
