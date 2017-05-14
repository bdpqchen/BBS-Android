package com.twtstudio.bbs.bdpqchen.bbs.main.topTen;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;


import java.util.List;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class TopTenContract {
    public interface View extends BaseView {
        void addAnnounce(List<TopTenModel.AnnounceBean> announceBeen);
        void refreshAnnounce(List<TopTenModel.AnnounceBean> announceBeen);
        void failedToGetTopTen(String msg);
    }
    interface Presenter extends BasePresenter<TopTenContract.View> {
        void refreshAnnounce();
        void addAnnounce();
    }
}
