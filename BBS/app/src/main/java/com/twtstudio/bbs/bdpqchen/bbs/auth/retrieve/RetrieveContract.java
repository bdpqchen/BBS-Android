package com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;

/**
 * Created by bdpqchen on 17-5-21.
 */

interface RetrieveContract {
    interface View extends BaseView {
        void retrieveFailed(String msg);

        void retrieveSuccess(RetrieveModel model);

        void resetFailed(String msg);

        void resetSuccess(BaseModel model);
    }

    interface Presenter extends BasePresenter {
        void doRetrievePassword(Bundle bundle);

        void resetPassword(Bundle bundle);
    }
}
