package com.twtstudio.bbs.bdpqchen.bbs.main;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-6-5.
 */

public interface MainContract {
    interface View extends BaseView{
        void onGotDataList(MainModel model);
        void onGotDataFailed(String msg);
    }
    interface Presenter extends BasePresenter<View>{
        void getDataList();
    }
}
