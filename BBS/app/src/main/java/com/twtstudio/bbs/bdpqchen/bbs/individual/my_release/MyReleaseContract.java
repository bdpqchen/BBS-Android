package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by Arsener on 2017/5/13.
 */

interface MyReleaseContract {
    interface View extends BaseView {
        void clearMyReleaseList();
        void showMyReleaseList(List<MyReleaseModel> data);
        void onError(Throwable throwable);
    }
    interface Presenter extends BasePresenter<View> {
        void initMyReleaseList();
        void getMyReleaseList();
    }
}
