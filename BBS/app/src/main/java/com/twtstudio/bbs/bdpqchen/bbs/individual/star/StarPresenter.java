package com.twtstudio.bbs.bdpqchen.bbs.individual.star;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-6-29.
 */

class StarPresenter extends RxPresenter implements StarContract.Presenter {
    private StarContract.View mView;

    StarPresenter(StarContract.View view) {
        mView = view;
    }

    @Override
    public void getStarList() {
        SimpleObserver<List<StarModel>> observer = new SimpleObserver<List<StarModel>>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onGetStarFailed(msg);
            }

            @Override
            public void _onNext(List<StarModel> starModel) {
                if (mView != null)
                    mView.onGetStarList(starModel);
            }
        };
        addSubscribe(sHttpClient.getStarThreadList()
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void starThread(int tid, int position) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onStarFailed(msg);
            }

            @Override
            public void _onNext(BaseModel starModelBaseResponse) {
                if (mView != null)
                    mView.onStar(position);
            }
        };
        addSubscribe(sHttpClient.starThread(tid)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    @Override
    public void unStarThread(int tid, int position) {
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null)
                    mView.onUnStarFailed(msg);
            }

            @Override
            public void _onNext(BaseModel starModelBaseResponse) {
                if (mView != null)
                    mView.onUnStar(position);
            }
        };
        addSubscribe(sHttpClient.unStarThread(tid)
                .map(new ResponseTransformer<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
