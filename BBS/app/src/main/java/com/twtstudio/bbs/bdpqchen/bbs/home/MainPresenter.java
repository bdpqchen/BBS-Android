package com.twtstudio.bbs.bdpqchen.bbs.home;


import com.twtstudio.bbs.bdpqchen.bbs.commons.base.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import javax.inject.Inject;


/**
 * Created by bdpqchen on 17-4-21.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    @Inject
    public MainPresenter(){
        LogUtil.d("MainPresenter is injected");
    }

    @Override
    public void checkUpdate(int currentVersionCode) {
//        mView.showUpdateDialog(1);
//        checkNotNull(currentVersionCode);

        LogUtil.d("show the method--> checkUpdate()");
    }
}
