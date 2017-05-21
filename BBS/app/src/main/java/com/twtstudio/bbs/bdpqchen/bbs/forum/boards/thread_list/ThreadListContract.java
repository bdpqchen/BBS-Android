package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadModel;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-20.
 */

public interface ThreadListContract {
    interface View extends BaseView {
        void setThreadList(ThreadListModel threadListModel);
        void showErrorMessage(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getThreadList(int id, int page);
    }
}
