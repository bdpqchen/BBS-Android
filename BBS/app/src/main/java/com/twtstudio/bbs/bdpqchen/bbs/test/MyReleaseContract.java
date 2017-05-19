package com.twtstudio.bbs.bdpqchen.bbs.test;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by Arsener on 2017/5/13.
 */

interface MyReleaseContract {
    interface View extends BaseView {
        void getMyReleaseList(List<MyReleaseBean> data);

        void getMore(List<MyReleaseBean> data);

        void onError(Throwable throwable);
    }
    interface Presenter extends BasePresenter<View> {
    }
}
