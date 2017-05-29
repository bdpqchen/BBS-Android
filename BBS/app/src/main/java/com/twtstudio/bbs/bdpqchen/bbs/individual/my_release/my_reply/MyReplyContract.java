package com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by Arsener on 2017/5/28.
 */

public interface MyReplyContract {
    interface View extends BaseView {
        void clearMyReplyList();
        void showMyReplyList(List<MyReplyModel> data);
        void onError(Throwable throwable);
    }
    interface Presenter extends BasePresenter<View> {
        void initMyReplyList();
        void getMyReplyList();
    }
}
