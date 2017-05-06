package com.twtstudio.bbs.bdpqchen.bbs.auth.login;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-2.
 */

public interface LoginContract {

    interface View extends BaseView {
        void loginResults();
    }

    interface Presenter{
        void doLogin();
    }

}

