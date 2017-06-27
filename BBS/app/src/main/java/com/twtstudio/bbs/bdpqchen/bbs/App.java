package com.twtstudio.bbs.bdpqchen.bbs;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.HandlerThread;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.oubowu.slideback.ActivityHelper;
import com.pgyersdk.crash.PgyCrashManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.AppComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.DaggerAppComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.AppModule;

/**
 * Created by bdpqchen on 17-4-17.
 */

public class App extends Application {

    private Context mContext;
    private LogLevel mLogLevel = LogLevel.FULL;
    private ActivityHelper mActivityHelper;
    private static App sApplication;
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sApplication = this;

        PgyCrashManager.register(this);

        initLogUtils();
        initSlideBack();
        HandlerThread workerThread = new HandlerThread("global_worker_thread");
        workerThread.start();
    }

    private void initLogUtils() {
        if (!isApkDebug(mContext)) {
            mLogLevel = LogLevel.NONE;
        }
        Hawk.init(mContext).build();
        Logger.init()
                .logLevel(mLogLevel)
                .methodCount(3);

    }

    //滑动返回
    private void initSlideBack() {
        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);
    }

    public static ActivityHelper getActivityHelper() {
        return sApplication.mActivityHelper;
    }


    public static boolean isApkDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception ignored) {

        }
        return false;
    }

    public static AppComponent getAppComponent() {
        if (sAppComponent == null) {
            sAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(sApplication))
                    .build();
        }
        return sAppComponent;
    }


}
