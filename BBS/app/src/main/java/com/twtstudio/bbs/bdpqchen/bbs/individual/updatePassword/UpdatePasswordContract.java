package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.UpdatePasswordBean;

/**
 * Created by bdpqchen on 17-5-6.
 */

interface UpdatePasswordContract {

    interface View extends BaseView {
        void updatePasswordSuccess();

        void updatePasswordError(String errorMsg);

        boolean checkInput();
    }

    interface Presenter extends BasePresenter<View> {
        void sendUpdatePasswordInfo(String oldPassword, String newPassword);

        void dealFeedbackInfo(UpdatePasswordBean updatePasswordBean);
    }

}
