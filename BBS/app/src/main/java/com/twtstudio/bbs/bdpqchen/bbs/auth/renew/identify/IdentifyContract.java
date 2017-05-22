package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-21.
 */

interface IdentifyContract {
    interface View extends BaseView{
        void identifyFailed(String m);
        void identifySuccess(IdentifyModel model);
    }
    interface Presenter extends BasePresenter<View>{
        void doIdentify(String username, String password);
    }
}
