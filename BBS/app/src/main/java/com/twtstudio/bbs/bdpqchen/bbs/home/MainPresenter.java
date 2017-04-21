package com.twtstudio.bbs.bdpqchen.bbs.home;

import android.support.annotation.NonNull;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.RxPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by bdpqchen on 17-4-21.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    @Override
    public void checkUpdate(int currentVersionCode) {
//        mView.showUpdateDialog(1);
//        checkNotNull(currentVersionCode);

    }
}
