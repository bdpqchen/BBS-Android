package com.twtstudio.bbs.bdpqchen.bbs.home;


import com.twtstudio.bbs.bdpqchen.bbs.commons.base.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtils;

import javax.inject.Inject;


/**
 * Created by bdpqchen on 17-4-21.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    @Inject
    public MainPresenter(){
        LogUtils.d("MainPresenter is injected");
    }

    @Override
    public void checkUpdate(int currentVersionCode) {
//        mView.showUpdateDialog(1);
//        checkNotNull(currentVersionCode);

        LogUtils.d("show the method--> checkUpdate()");
    }
}
