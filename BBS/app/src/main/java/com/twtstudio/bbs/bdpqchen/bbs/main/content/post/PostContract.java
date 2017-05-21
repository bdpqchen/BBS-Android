package com.twtstudio.bbs.bdpqchen.bbs.main.content.post;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by zhangyulong on 5/20/17.
 */

public class PostContract {
    interface View extends BaseView {
        void PostSuccess(IndexPostModel IndexPostModel);
        void PostFailed(String msg);

    }

    interface Presenter{
        void doPost(String threadid,String comment);
    }
}
