package com.twtstudio.bbs.bdpqchen.bbs.commons;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.twtstudio.bbs.bdpqchen.bbs.BuildConfig;
import com.twtstudio.bbs.bdpqchen.bbs.commons.tools.UpdateTool;

import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.TrackerConfig;

import java.util.NoSuchElementException;

import timber.log.Timber;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_HOME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_USER_ID;

/**
 * Created by bdpqchen on 17-4-17.
 */

public class App extends Application {

    private static Context mContext;
    private LogLevel mLogLevel = LogLevel.FULL;
//    private ActivityHelper mActivityHelper;
//    private static App sApplication;

    public static Context getContext() {
        if (mContext != null) {
            return mContext;
        }
        throw new NoSuchElementException("No context from App.class");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        sApplication = this;

        UpdateTool.initBuglyReport(this);

        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        initLogUtils();
//        initSlideBack();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        initPiwik();
    }

    private void initPiwik() {
        Timber.plant(new Timber.DebugTree());
        getTracker().setUserId(PK_USER_ID);
        getTracker().setApplicationDomain(PK_HOME);
        getTracker().setDispatchTimeout(100000);
        //TrackHelper.track().download().identifier(new DownloadTracker.Extra.ApkChecksum(this)).with(getTracker());
        // getTracker().download();
    }

    private Tracker mPiwikTracker;

    public Piwik getPiwik() {
        return Piwik.getInstance(this);
    }

    public synchronized Tracker getTracker() {

        if (mPiwikTracker == null) mPiwikTracker = getPiwik().newTracker(onCreateTrackerConfig());
        return mPiwikTracker;
    }

    public TrackerConfig onCreateTrackerConfig() {
        return TrackerConfig.createDefault("https://elf.twtstudio.com/piwik.php", 13);
    }


    @Override
    public void onTrimMemory(int level) {
        if ((level == TRIM_MEMORY_UI_HIDDEN || level == TRIM_MEMORY_COMPLETE) && mPiwikTracker != null) {
            mPiwikTracker.dispatch();
        }
        super.onTrimMemory(level);
    }


    private void initLogUtils() {
        if (BuildConfig.DEBUG) {
            mLogLevel = LogLevel.NONE;
        }
        Hawk.init(mContext).build();
        Logger.init()
                .logLevel(mLogLevel)
                .methodCount(3);

    }
/*
    //滑动返回
    private void initSlideBack() {
        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);
    }

    public static ActivityHelper getActivityHelper() {
        return sApplication.mActivityHelper;
    }*/


}
