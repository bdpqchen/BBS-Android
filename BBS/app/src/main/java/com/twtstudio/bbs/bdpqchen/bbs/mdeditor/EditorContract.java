package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;

/**
 * Created by bdpqchen on 17-9-11.
 */

interface EditorContract {
    interface View extends BaseView{
        void onUpload(UploadImageModel entity);
        void onUploadFailed(String msg);
    }
    interface Presenter extends BasePresenter{
        void uploadImage(String uri);
    }
}
