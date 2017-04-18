package com.twtstudio.bbs.bdpqchen.bbs.home;

import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.AppActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LogUtils.d("ssss");

        Logger.t("tagggg").i("-=========", "ssssss");
//        Logger.t("myTag").d("MAINActivity");
//        Logger.d("tag", "MAINACTIVITY");

//        AppActivityManager.getActivityManager();

    }
}
