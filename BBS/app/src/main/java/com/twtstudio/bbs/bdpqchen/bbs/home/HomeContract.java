package com.twtstudio.bbs.bdpqchen.bbs.home;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;

/**
 * Created by bdpqchen on 17-4-21.
 */

public interface HomeContract {

    interface View extends BaseView{
        void showUpdateDialog(int versionCode);
        void showIndividualInfo(IndividualInfoModel individualInfoModel);
    }

    interface Presenter extends BasePresenter<View>{
        void checkUpdate(int currentVersionCode);
        void initIndividualInfo();
    }
}
