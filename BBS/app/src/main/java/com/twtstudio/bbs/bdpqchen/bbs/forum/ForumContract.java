package com.twtstudio.bbs.bdpqchen.bbs.forum;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-11.
 */

interface ForumContract {
    interface View extends BaseView{
        void onGotForumBoard(ForumBoardModel forumBoardList);
        void getForumBoardFailed(String msg);
    }
    interface Presenter extends BasePresenter{
        void getForumBoardList();
    }
}
