package com.twtstudio.bbs.bdpqchen.bbs.home;


import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import javax.inject.Inject;


/**
 * Created by bdpqchen on 17-4-21.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter{

    private RxDoHttpClient mHttpClient;

    @Inject
    public HomePresenter(RxDoHttpClient httpClient){
        LogUtil.d("HomePresenter is injected");
        this.mHttpClient = httpClient;
        mHttpClient.getDataList();
    }

    private void getDataList(){
//        addSubscribe(mHttpClient.getDataList());
    }

    @Override
    public void checkUpdate(int currentVersionCode) {
//        mView.showUpdateDialog(1);
//        checkNotNull(currentVersionCode);

//        addSubscribe();
        LogUtil.d("show the method--> checkUpdate()");
    }


    @Override
    public void initIndividualInfo() {

    }
}
