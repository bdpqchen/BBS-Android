package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.PostModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

/**
 * Created by bdpqchen on 17-5-12.
 */

interface ThreadContract {
    interface View extends BaseView{
        void onGotThread(ThreadModel model);
        void onGetThreadFailed(String m);
        void onCommentFailed(String m);
        void onCommented(PostModel model);
        void onStarFailed(String m);
        void onUnStarFailed(String m);
        void onStarred();
        void onUnStarred();
        void onUploadFailed(String m);
        void onUploaded(UploadImageModel model);
        void onLike(BaseModel model);
        void onLikeFailed(String m, int position, boolean isLike);
        void onUnlike(BaseModel entity);
        void onUnlikeFailed(String m, int position, boolean isLike);

    }
    interface Presenter extends BasePresenter<View>{
        void getThread(int threadId, int postPage);
        void doComment(int threadId, String comment, int replyId, boolean isAnonymous);
        void starThread(int id);
        void unStarThread(int id);
        void uploadImages(String uri);
        void like(int id, int position, boolean isLike, boolean isPost);
    }
}
