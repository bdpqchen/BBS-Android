package com.twtstudio.bbs.bdpqchen.bbs.auth.register;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-2.
 */

public interface RegisterContract {
    interface View extends BaseView {
        void registerSuccess();

        void registerFailed(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void doRegister(Bundle bundle);
    }
}
