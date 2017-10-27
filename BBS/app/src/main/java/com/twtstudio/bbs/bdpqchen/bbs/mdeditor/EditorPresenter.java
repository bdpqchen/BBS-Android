package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-9-11.
 */

public class EditorPresenter extends RxPresenter implements EditorContract.Presenter {
    private EditorContract.View mView;

    EditorPresenter(EditorContract.View v) {
        mView = v;
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
        addSubscribe(sHttpClient.uploadImage(uri)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );

    }


}
