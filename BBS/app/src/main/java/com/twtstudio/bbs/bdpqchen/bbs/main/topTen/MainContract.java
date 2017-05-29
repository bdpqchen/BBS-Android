package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.main.model.MainModel;


import java.util.List;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class MainContract {
    public interface View extends BaseView {
        void onGotHomeData(MainModel model);
        void onFailedGetHomeData(String msg);
    }
    interface Presenter extends BasePresenter<MainContract.View> {
        void getHomeDataList();
    }
}
