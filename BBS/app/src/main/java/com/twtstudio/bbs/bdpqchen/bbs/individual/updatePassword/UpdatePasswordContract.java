package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;

/**
 * Created by bdpqchen on 17-5-6.
 */

interface UpdatePasswordContract {

    interface View extends BaseView {
        void onUpdated(BaseModel model);
        void onUpdateFailed(String errorMsg);

    }

    interface Presenter extends BasePresenter {
        void doUpdatePass(String newPass, String oldPass);
    }

}
