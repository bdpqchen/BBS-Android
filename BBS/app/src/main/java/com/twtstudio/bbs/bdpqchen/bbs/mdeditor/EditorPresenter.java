package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-9-11.
 */

public class EditorPresenter extends RxPresenter<EditorContract.View> implements EditorContract.Presenter {

    private RxDoHttpClient<UploadImageModel> mHttpClient;

    @Inject
    EditorPresenter(RxDoHttpClient client) {
        mHttpClient = client;
    }

    @Override
    public void uploadImage(String uri) {

        SimpleObserver<UploadImageModel> observer = new SimpleObserver<UploadImageModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onUploadFailed(msg);
            }

            @Override
            public void _onNext(UploadImageModel model) {
                if (mView != null)
                    mView.onUpload(model);

            }
        };
        addSubscribe(mHttpClient.uploadImage(uri)
                .map(mHttpClient.mTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }


}
