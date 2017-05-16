package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualContract;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualMessageModel;

import java.util.List;

/**
 * Created by Ricky on 2017/5/13.
 */

public interface MessageContract {


    interface View extends BaseView {
        void showMessageList(List<IndividualMessageModel> messageList);
    }

    interface Presenter extends BasePresenter<MessageContract.View> {
        void getMessageList();
    }
}
