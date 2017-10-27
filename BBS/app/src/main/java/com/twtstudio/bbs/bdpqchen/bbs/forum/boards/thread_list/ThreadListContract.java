package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-20.
 */

public interface ThreadListContract {
    interface View extends BaseView {
        void setThreadList(ThreadListModel threadListModel);

        void showErrorMessage(String msg);
    }

    interface Presenter extends BasePresenter {
        void getThreadList(int id, int page);
    }
}
