package com.twtstudio.bbs.bdpqchen.bbs.main;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.main.hot.HotEntity;
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestEntity;

import java.util.List;

/**
 * Created by bdpqchen on 17-6-5.
 */

public interface MainContract {
    interface View extends BaseView {
        void onGetLatestList(List<LatestEntity> list);

        void onGetHotList(List<HotEntity> list);

        void onGotDataFailed(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getLatestList();
        void getHotList();
    }
}
