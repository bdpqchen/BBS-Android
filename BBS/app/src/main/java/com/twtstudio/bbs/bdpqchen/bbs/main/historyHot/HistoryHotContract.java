package com.twtstudio.bbs.bdpqchen.bbs.main.historyHot;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class HistoryHotContract {
    public interface View extends BaseView {
        void addAnnounce(List<HistoryHotModel.DataBean.HistoryhotBean> announceBeen);
        void refreshAnnounce(List<HistoryHotModel.DataBean.HistoryhotBean> announceBeen);
        void failedToGetHistoryHot(String msg);
    }
    interface Presenter extends BasePresenter<HistoryHotContract.View> {
        void refreshAnnounce();
        void addAnnounce();
    }
}
