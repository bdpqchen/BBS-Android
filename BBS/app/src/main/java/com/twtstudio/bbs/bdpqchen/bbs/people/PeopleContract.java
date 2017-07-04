package com.twtstudio.bbs.bdpqchen.bbs.people;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-7-3.
 */

public interface PeopleContract {
    interface View extends BaseView{
        void onGetUserInfo(PeopleModel model);
        void onGetUserInfoFailed(String m);
    }
    interface Presenter extends BasePresenter<View>{
        void getUserInfo(int uid);
    }
}
