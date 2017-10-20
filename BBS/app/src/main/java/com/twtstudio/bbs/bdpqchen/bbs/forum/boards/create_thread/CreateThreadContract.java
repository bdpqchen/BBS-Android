package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread;

import android.os.Bundle;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-27.
 */

interface CreateThreadContract {
    interface View extends BaseView {
        void onPublished();

        void onPublishFailed(String msg);

        void onUploadFailed(String m);

        void onUploaded(UploadImageModel model);

        void onGetBoardList(BoardsModel model);

        void onGetBoardListFailed(String m);

        void onGetForumList(List<ForumModel> list);

        void onGetForumFailed(String m);
    }

    interface Presenter extends BasePresenter {
        void doPublishThread(Bundle bundle);

        void uploadImages(String uri);

        void getBoardList(int forumId);

        void getForumList();
    }
}
