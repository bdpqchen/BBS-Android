package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import java.util.List;

/**
 * Created by Ricky on 2017/5/13.
 */

interface MessageContract {


    interface View extends BaseView {
        void onGetMessageFailed(String m);
        void showMessageList(List<MessageModel> messageList);
        void onCleared();
        void onClearFailed(String msg);
    }

    interface Presenter extends BasePresenter<MessageContract.View> {
        void getMessageList(int page);
        void doClearUnreadMessage();
    }
}
