package com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;

import java.util.List;

/**
 * Created by bdpqchen on 17-9-18.
 */

public interface PublishContract {

    interface Presenter extends BasePresenter {
        void getPublishList(int page);
        void deleteThread(int tid, int position);
    }

    interface View extends BaseView {
        void onGetPublishList(List<PublishEntity> entityList);
        void onGetPublishFailed(String m);
        void onDeleteThread(BaseModel entity, int position);
        void onDeleteThreadFailed(String m);
    }
}
