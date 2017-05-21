package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-21.
 */

interface AppealPassportContract {
    interface View extends BaseView{
        void sendFailed(String s);
        void sendSuccess();
    }

    interface Presenter extends BasePresenter<View>{
        void appealPassport(Bundle bundle);

    }
}
