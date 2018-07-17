package com.twtstudio.bbs.bdpqchen.bbs.individual.star;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by bdpqchen on 17-6-29.
 */

interface StarContract {
    interface View extends BaseView{
        void onGetStarList(List<StarModel> list);
        void onGetStarFailed(String m);
        void
        onStar(int position);
        void onStarFailed(String m);
        void onUnStar(int position);
        void onUnStarFailed(String s);
    }
    interface Presenter extends BasePresenter{
        void getStarList();
        void starThread(int tid, int position);
        void unStarThread(int tid, int position);
    }
}
