package com.twtstudio.bbs.bdpqchen.bbs.forum.forum;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-11.
 */

interface ForumContract {
    interface View extends BaseView{
        void showForumList(List<ForumModel> forumModel);
        void failedToGetForum(String msg);
    }
    interface Presenter extends BasePresenter<View>{
        void getForumList();
    }
}
