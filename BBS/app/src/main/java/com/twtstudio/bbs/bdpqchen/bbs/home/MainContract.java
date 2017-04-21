package com.twtstudio.bbs.bdpqchen.bbs.home;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-4-21.
 */

public interface MainContract {

    interface View extends BaseView{
        void showUpdateDialog(int versionCode);
    }

    interface Presenter extends BasePresenter<View>{
        void checkUpdate(int currentVersionCode);
    }
}
