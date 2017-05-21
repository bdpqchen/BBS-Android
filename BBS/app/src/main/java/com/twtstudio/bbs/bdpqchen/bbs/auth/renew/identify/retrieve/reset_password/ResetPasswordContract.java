package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.reset_password;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;

/**
 * Created by bdpqchen on 17-5-21.
 */

interface ResetPasswordContract {
    interface View extends BaseView {
        void resetFailed(String m);
        void resetSuccess(BaseModel response);
    }
    interface Presenter extends BasePresenter<View> {
        void resetPassword(Bundle bundle);
    }
}
