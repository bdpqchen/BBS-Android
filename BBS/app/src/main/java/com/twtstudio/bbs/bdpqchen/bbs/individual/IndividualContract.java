package com.twtstudio.bbs.bdpqchen.bbs.individual;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;

/**
 * Created by bdpqchen on 17-5-4.
 */

public interface IndividualContract {

    interface View extends BaseView {
        void gotInfo(IndividualInfoModel model);
        void getInfoFailed(String m);
        void onGotMessageCount(int integer);
        void onGetMessageFailed(String m);

    }

    interface Presenter extends BasePresenter<View>{
        void getUnreadMessageCount();
        void initIndividualInfo();
    }
}
