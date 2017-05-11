package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.BaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-11.
 */

class BoardsPresenter extends RxPresenter<BoardsContract.View> implements BoardsContract.Presenter {

    private RxDoHttpClient<BoardsModel> mHttpClient;
//    public ResponseTransformer<List<BoardsModel>> mTransformer = new ResponseTransformer<>();


    @Inject
    BoardsPresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;

    }


    @Override
    public void getBoardList(final int boardId) {

      /*  SimpleObserver<BoardsModel> observer = new SimpleObserver<BoardsModel>() {

            @Override
            public void _onError(String msg) {
//                mView.failedToGetForum(msg);
                LogUtil.dd("get board list onNext()");
            }

            @Override
            public void _onNext(BoardsModel boardsModel) {

            }

        };*/


        /*addSubscribe(mHttpClient.getBoardList(boardId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<BaseResponse<BoardsModel>>() {
                            @Override
                            public void accept(@NonNull BaseResponse<BoardsModel> boardsModelBaseResponse) throws Exception {
                                boardsModelBaseResponse.getData().getBoards().get(0).getId();
                            }
                        })
                        .observeOn(Schedulers.io())
                        .flatMap(new Function<BaseResponse<BoardsModel>, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull BaseResponse<BoardsModel> boardsModelBaseResponse) throws Exception {
                                List<BoardsModel> boardList = boardsModelBaseResponse.getData().getBoards();
                                mHttpClient.getThreadList()
                                return null;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer)
        );*/
    }
}
