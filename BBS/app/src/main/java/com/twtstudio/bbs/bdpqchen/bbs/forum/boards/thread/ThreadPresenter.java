package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import com.twtstudio.bbs.bdpqchen.bbs.bbkit.bbcode.BBCodeParse;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.ResponseTransformer;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.SimpleObserver;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.PostModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient.BASE_URL;

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
                .map(threadModel -> {
                    if (threadModel != null) {
                        // TODO: 17-6-9 取消对bbcode的支持
                        if (threadModel.getThread() != null) {
                            ThreadModel.ThreadBean thread = threadModel.getThread();
                            String content = thread.getContent();
                            if (content != null && content.length() > 0) {
                                if (content.contains("[/") && content.contains("[") && content.contains("]")) {
                                    content = BBCodeParse.bbcode2Html(content);
                                }
                                content = content.replaceAll("attach:", BASE_URL + "img/");
                                String key = "![]";
                                if (content.contains(key)){
                                    int index = content.indexOf(key);
                                    String start = content.substring(0, index);
                                    String end = content.substring(index, content.length());
                                    content = start + "\n" + end;
                                }
                                content = content.replaceAll("!\\[]", "\n![]");
//                                LogUtil.dd("Content in map thread", content);
                                thread.setContent(content);
                            }
                        }
                        if (threadModel.getPost() != null && threadModel.getPost().size() > 0) {
                            List<ThreadModel.PostBean> postList = threadModel.getPost();
                            for (int i = 0; i < postList.size(); i++) {
                                String content = postList.get(i).getContent();
                                if (content != null && content.length() > 0) {
                                    if (content.contains("[/") && content.contains("[") && content.contains("]")) {
                                        content = BBCodeParse.bbcode2Html(content);
                                    }
                                    content = content.replaceAll("attach:", BASE_URL + "img/");
//                                    LogUtil.dd("content before", content);
                                    content = content.replaceAll("!\\[]", "\n![]");
//                                    LogUtil.dd("Content in map", content);
                                    postList.get(i).setContent(content);
                                }
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
        addSubscribe(mHttpClient.uploadImage(uri)
                .map(transformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

}
