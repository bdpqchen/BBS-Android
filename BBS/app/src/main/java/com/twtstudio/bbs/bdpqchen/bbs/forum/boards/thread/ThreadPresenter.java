package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.BaseResponse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.PostModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bdpqchen on 17-5-12.
 */

class ThreadPresenter extends RxPresenter implements ThreadContract.Presenter {
    private ThreadContract.View mView;

    ThreadPresenter(ThreadContract.View view) {
        mView = view;
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
        addSubscribe(sHttpClient.getThread(threadId, postPage)
                .map(new ResponseTransformer<>())
                .map(threadModel -> {
                    if (threadModel != null) {
                        if (threadModel.getThread() != null) {
                            threadModel.getThread().setContent_converted(TextUtil.convert2HtmlContent(threadModel.getThread().getContent()));
                        }
                        if (threadModel.getPost() != null && threadModel.getPost().size() > 0) {
                            List<ThreadModel.PostBean> postList = threadModel.getPost();
                            for (int i = 0; i < postList.size(); i++) {
                                String content = postList.get(i).getContent();
                                postList.get(i).setContent_converted(TextUtil.convert2HtmlContent(content));
                            }
                        }
                    }
                    return threadModel;
                })
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
        addSubscribe(sHttpClient.doComment(threadId, comment, replyId, isAno)
                .map(new ResponseTransformer<>())
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
        addSubscribe(sHttpClient.starThread(id)
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
        addSubscribe(sHttpClient.unStarThread(id)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    public void uploadImages(String uri) {
        ResponseTransformer<UploadImageModel> transformer = new ResponseTransformer<>();
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
        addSubscribe(sHttpClient.uploadImage(uri)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void like(int id, int position, boolean isLike, boolean isThread) {
        ResponseTransformer<BaseModel> transformer = new ResponseTransformer<>();
        SimpleObserver<BaseModel> observer = new SimpleObserver<BaseModel>() {
            @Override
            public void _onError(String msg) {
                if (mView != null) {
                    if (isLike) mView.onLikeFailed(msg, position, true);
                    else mView.onUnlikeFailed(msg, position, false);
                }
            }

            @Override
            public void _onNext(BaseModel o) {
                if (mView != null) {
                    if (isLike) mView.onLike(o);
                    else mView.onUnlike(o);
                }
            }
        };
        addSubscribe(getLikeHeader(id, isLike, isThread)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    private Observable<BaseResponse<BaseModel>> getLikeHeader(int id, boolean isLike, boolean isPost) {
        if (isPost) {
            if (isLike) return sHttpClient.likePost(id);
            else return sHttpClient.unlikePost(id);
        } else {
            if (isLike) return sHttpClient.likeThread(id);
            else return sHttpClient.unlikeThread(id);
        }
    }

}
