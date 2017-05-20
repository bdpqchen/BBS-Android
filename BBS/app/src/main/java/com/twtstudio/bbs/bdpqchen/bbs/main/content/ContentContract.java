package com.twtstudio.bbs.bdpqchen.bbs.main.content;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by zhangyulong on 5/19/17.
 */

public class ContentContract  {
    interface View extends BaseView {
        void showPost(List<ContentModel.DataBean.PostBean> post);
        void showThread(ContentModel.DataBean.ThreadBean thread);
        void failedToGetContent(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(String threadid);
    }
}
