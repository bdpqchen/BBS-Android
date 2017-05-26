package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.BaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-11.
 */

class BoardsPresenter extends RxPresenter<BoardsContract.View> implements BoardsContract.Presenter {

    private RxDoHttpClient<BoardsModel> mHttpClient;
    public ResponseTransformer<BoardsModel> mTransformer = new ResponseTransformer<>();
    public ResponseTransformer<ThreadListModel> mTransformerThread = new ResponseTransformer<>();


    @Inject
    BoardsPresenter(RxDoHttpClient httpClient) {
        mHttpClient = httpClient;

    }


    @Override
    public void getBoardList(final int boardId) {

        SimpleObserver<List<PreviewThreadModel>> observer = new SimpleObserver<List<PreviewThreadModel>>() {

            @Override
            public void _onError(String msg) {
                mView.failedToGetBoardList(msg);
                LogUtil.dd("error_message", msg);
            }

            @Override
            public void _onNext(List<PreviewThreadModel> previewThreadModels) {
                mView.setBoardList(previewThreadModels);
                LogUtil.dd("OnNext()");
            }

        };


        addSubscribe(mHttpClient.getBoardList(boardId)
                .map(mTransformer)
                .flatMap(new Function<BoardsModel, Observable<BoardsModel.BoardsBean>>() {
                    @Override
                    public Observable<BoardsModel.BoardsBean> apply(@NonNull BoardsModel boardsModel) throws Exception {
                        LogUtil.dd("apply()", String.valueOf(boardsModel.getBoards().size()));
                        return Observable.fromIterable(boardsModel.getBoards());

                    }
                })
                .flatMap(new Function<BoardsModel.BoardsBean, Observable<BaseResponse<ThreadListModel>>>() {
                    @Override
                    public Observable<BaseResponse<ThreadListModel>> apply(@NonNull BoardsModel.BoardsBean boardsBean) throws Exception {
                        return mHttpClient.getThreadList(boardsBean.getId(), 0);
                    }
                })
                .map(mTransformerThread)
                .map(threadListModel -> {
                    List<PreviewThreadModel> previewThreadList = new ArrayList<>();
                    int listSize = threadListModel.getBoard().getC_thread();
                    LogUtil.dd(String.valueOf(listSize));
                    PreviewThreadModel model = new PreviewThreadModel();
                    model.setBoard(threadListModel.getBoard());
                    if (listSize > 2) {
                        List<ThreadListModel.ThreadBean> threadBeanList = new ArrayList<>();
                        ThreadListModel.ThreadBean threadBean0 = threadListModel.getThread().get(0);
                        ThreadListModel.ThreadBean threadBean1 = threadListModel.getThread().get(1);
                        threadBeanList.add(threadBean0);
                        threadBeanList.add(threadBean1);
                        model.setThreadList(threadBeanList);
                    }
                    previewThreadList.add(model);
                    return previewThreadList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }
}
