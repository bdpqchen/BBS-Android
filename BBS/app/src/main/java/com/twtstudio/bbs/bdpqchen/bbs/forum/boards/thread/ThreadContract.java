package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-12.
 */

interface ThreadContract {
    interface View extends BaseView{
        void showThread(ThreadModel model);
        void showFailed(String m);
        void onCommentFailed(String m);
        void onCommented(PostModel model);
        void onStarFailed(String m);
        void onUnStarFailed(String m);
        void onStarred();
        void onUnStarred();

    }
    interface Presenter extends BasePresenter<View>{
        void getThread(int threadId, int postPage);
        void doComment(int threadId, String comment);
        void starThread(int id);
        void unStarThread(int id);
    }
}
