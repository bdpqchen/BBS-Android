package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-27.
 */

class CreateThreadPresenter extends RxPresenter<CreateThreadContract.View> implements CreateThreadContract.Presenter {

    private RxDoHttpClient<CreateThreadModel> mHttpClient;

    @Inject
    CreateThreadPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }

    @Override
    public void doPublishThread(Bundle bundle) {
        SimpleObserver<CreateThreadModel> observer = new SimpleObserver<CreateThreadModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onPublishFailed(msg);
            }

            @Override
            public void _onNext(CreateThreadModel model) {
                if (mView != null)
                    mView.onPublished();
            }
        };
        addSubscribe(mHttpClient.doPublishThread(bundle)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void uploadImages(String uri) {
        SimpleObserver<UploadImageModel> observer = new SimpleObserver<UploadImageModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.onUploadFailed(msg);
                }
            }

            @Override
            public void _onNext(UploadImageModel o) {
                if (mView != null) {
                    mView.onUploaded(o);
                }
            }
        };
        addSubscribe(mHttpClient.uploadImage(uri)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void getBoardList(int forumId) {
        SimpleObserver<BoardsModel> observer = new SimpleObserver<BoardsModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.onGetBoardListFailed(msg);
                }
            }

            @Override
            public void _onNext(BoardsModel o) {
                if (mView != null) {
                    mView.onGetBoardList(o);
                }
            }
        };
        addSubscribe(mHttpClient.getBoardList(forumId)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void getForumList() {
        SimpleObserver<List<ForumModel>> observer = new SimpleObserver<List<ForumModel>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    mView.onGetForumFailed(msg);
                }
            }

            @Override
            public void _onNext(List<ForumModel> forumModels) {
                if (mView != null) {
                    mView.onGetForumList(forumModels);
                }
            }
        };
        addSubscribe(mHttpClient.getForumList()
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));

    }

}
