package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

/**
 * Created by bdpqchen on 17-5-11.
 */

interface BoardsContract {

    interface View extends BaseView<Presenter> {
        void setBoardList(PreviewThreadModel previewThreadModel);
        void failedToGetBoardList(String msg);
        void onFailedGetSimpleList(String m);
        void onGotSimpleList(BoardsModel model);
    }

    interface Presenter extends BasePresenter{
        void getBoardList(int forumId);
        void getSimpleBoardList(int fid);

    }
}
