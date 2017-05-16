package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by Ricky on 2017/5/13.
 */

interface MessageContract {


    interface View extends BaseView {
        void showMessageList(List<MessageModel> messageList);
    }

    interface Presenter extends BasePresenter<MessageContract.View> {
        void getMessageList(int page);
    }
}
