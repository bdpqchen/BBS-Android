package com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-21.
 */

interface RetrieveContract {
    interface View extends BaseView {
        void retrieveFailed(String msg);
        void retrieveSuccess(RetrieveModel model);
    }
    interface Presenter extends BasePresenter<View> {
        void doRetrieveUsername(Bundle bundle);
    }
}
