package com.twtstudio.bbs.bdpqchen.bbs.auth.register.old;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-2.
 */

public interface RegisterOldContract {
    interface View extends BaseView {
        void registerSuccess();
        void registerFailed(String errorMessage);
    }

    interface Presenter extends BaseView {
        void doRegisterOld(Bundle bundle);
    }
}
