package com.twtstudio.bbs.bdpqchen.bbs.individual.friend;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by bdpqchen on 17-6-29.
 */

interface FriendContract {
    interface View extends BaseView{
        void onGetFriendList(List<FriendModel> list);
        void onGetFriendFailed(String s);
    }
    interface Presenter extends BasePresenter{
        void getFriendList();
    }
}
