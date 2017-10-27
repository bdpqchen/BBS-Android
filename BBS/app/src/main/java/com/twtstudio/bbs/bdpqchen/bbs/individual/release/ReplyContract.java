package com.twtstudio.bbs.bdpqchen.bbs.individual.release;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply.ReplyEntity;

import java.util.List;

/**
 * Created by bdpqchen on 17-9-18.
 */

public interface ReplyContract {

    interface Presenter extends BasePresenter {
        void onGetReplyList(List<ReplyEntity> entityList);
    }

    interface View extends BaseView {
        void getReplyList(int page);
    }
}
