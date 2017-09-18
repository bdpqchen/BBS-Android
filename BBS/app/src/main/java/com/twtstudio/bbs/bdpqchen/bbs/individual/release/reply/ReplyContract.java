package com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by bdpqchen on 17-9-18.
 */

public interface ReplyContract {

    interface Presenter extends BasePresenter<View> {
        void getReplyList(int page);
    }

    interface View extends BaseView {
        void onGetReplyList(List<ReplyEntity> entityList);
        void onGetReplyFailed(String m);
    }
}
