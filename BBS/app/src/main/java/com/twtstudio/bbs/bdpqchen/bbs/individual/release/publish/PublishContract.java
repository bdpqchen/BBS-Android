package com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;

import java.util.List;

/**
 * Created by bdpqchen on 17-9-18.
 */

public interface PublishContract {

    interface Presenter extends BasePresenter<View> {
        void getPublishList(int page);
    }

    interface View extends BaseView {
        void onGetPublishList(List<PublishEntity> entityList);
        void onGetPublishFailed(String m);
    }
}
