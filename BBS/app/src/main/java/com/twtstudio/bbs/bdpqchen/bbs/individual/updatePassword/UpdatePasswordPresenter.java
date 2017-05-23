package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.SimpleBean;

import javax.inject.Inject;

/**
 * Created by bdpqchen on 17-5-6.
 */

class UpdatePasswordPresenter extends RxPresenter<UpdatePasswordContract.View> implements UpdatePasswordContract.Presenter {

    UpdatePasswordContract.View view;
    UpdatePasswordClient updatePasswordClient = new UpdatePasswordClient(this);
    String uidToken = PrefUtil.getAuthUid() + "|" + PrefUtil.getAuthToken();

    @Inject
    UpdatePasswordPresenter() {
    }

    ;

    public UpdatePasswordPresenter(UpdatePasswordContract.View view) {
        this.view = view;
    }


    @Override
    public void sendUpdatePasswordInfo(String oldPassword, String newPassword) {
        updatePasswordClient.sendUpdatePasswordInfo(uidToken, oldPassword, newPassword);
    }

    @Override
    public void dealFeedbackInfo(SimpleBean updatePasswordBean) {
        if (updatePasswordBean.err == 0) {
            view.updatePasswordSuccess();
        } else {
            view.updatePasswordError(updatePasswordBean.data);
        }
    }
}
