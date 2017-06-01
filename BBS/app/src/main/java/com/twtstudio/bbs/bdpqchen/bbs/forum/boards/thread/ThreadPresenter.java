package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-12.
 */

class ThreadPresenter extends RxPresenter<ThreadContract.View> implements ThreadContract.Presenter {

    private RxDoHttpClient<ThreadModel> mHttpClient;
    private ResponseTransformer<PostModel> mTransformerPost = new ResponseTransformer<>();


    @Inject
    ThreadPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }


    @Override
    public void getThread(int threadId, int postPage) {
        SimpleObserver<ThreadModel> observer = new SimpleObserver<ThreadModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGetThreadFailed(msg);
            }

            @Override
            public void _onNext(ThreadModel model) {
                if (mView != null)
                    mView.onGotThread(model);
            }

        };
        addSubscribe(mHttpClient.getThread(threadId, postPage)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void doComment(int threadId, String comment, int replyId, boolean isAno) {
        SimpleObserver<PostModel> observer = new SimpleObserver<PostModel>() {
            @Override
            public void _onError(String msg) {
                mView.onCommentFailed(msg);
            }

            @Override
            public void _onNext(PostModel model) {
                mView.onCommented(model);
            }

        };
        addSubscribe(mHttpClient.doComment(threadId, comment, replyId, isAno)
                .map(mTransformerPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void starThread(int id) {
        ResponseTransformer<BaseModel> transformer = new ResponseTransformer<>();
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                mView.onStarFailed(msg);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                mView.onStarred();
            }
        };
        addSubscribe(mHttpClient.starThread(id)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void unStarThread(int id) {
        ResponseTransformer<BaseModel> transformer = new ResponseTransformer<>();
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                mView.onUnStarFailed(msg);
            }

            @Override
            public void _onNext(BaseModel baseModel) {
                mView.onUnStarred();
            }
        };
        addSubscribe(mHttpClient.unStarThread(id)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

}
