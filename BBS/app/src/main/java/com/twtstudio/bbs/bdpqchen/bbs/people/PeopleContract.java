package com.twtstudio.bbs.bdpqchen.bbs.people;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;

/**
 * Created by bdpqchen on 17-7-3.
 */

public interface PeopleContract {
    interface View extends BaseView{
        void onGetUserInfo(PeopleModel model);
        void onGetUserInfoFailed(String m);
        void onAddFriend(BaseModel model);
        void onAddFriendFailed(String s);
    }
    interface Presenter extends BasePresenter{
        void getUserInfo(int uid);
        void addFriend(int toUid, String msg);
    }
}
