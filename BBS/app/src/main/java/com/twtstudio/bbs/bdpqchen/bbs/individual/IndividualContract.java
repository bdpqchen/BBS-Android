package com.twtstudio.bbs.bdpqchen.bbs.individual;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;

/**
 * Created by bdpqchen on 17-5-4.
 */

public interface IndividualContract {

    interface View extends BaseView {

        void gotInfo(IndividualInfoModel model);
        void getInfoFailed(String m);
    }

    interface Presenter extends BasePresenter<View>{

        void initIndividualInfo();
    }
}
