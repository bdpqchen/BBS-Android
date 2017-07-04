package com.twtstudio.bbs.bdpqchen.bbs.individual.letter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-7-4.
 */

class LetterPresenter extends RxPresenter<LetterContract.View> implements LetterContract.Presenter{

    RxDoHttpClient<List<LetterModel>> mHttpClient;

    @Inject
    LetterPresenter(RxDoHttpClient httpClient){
        mHttpClient = httpClient;
    }


    @Override
    public void getLetterList(int uid, int page) {

            SimpleObserver<List<LetterModel>> observer = new SimpleObserver<List<LetterModel>>() {
                @Override
                public void _onError(String msg) {
                    if (mView != null)
                        mView.onGetLetterFailed(msg);
                }
                @Override
                public void _onNext(List<LetterModel> LetterModel) {
                    if (mView != null)
                        mView.onGetLetterList(LetterModel);
                }
            };
            addSubscribe(mHttpClient.getLetterList(uid, page)
                    .map(mHttpClient.mTransformer)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(observer)
            );
    }
}
