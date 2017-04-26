package com.twtstudio.bbs.bdpqchen.bbs;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.oubowu.slideback.ActivityHelper;
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

        if (!isApkDebug(mContext)){
            mLogLevel = LogLevel.NONE;
        }

        initApp();

        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);
        sApplication = this;

    }

    public static AppComponent getAppComponent(){
        if (sAppComponent == null){
            sAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(sApplication))
                    .build();
        }
        return sAppComponent;
    }

    public static ActivityHelper getActivityHelper(){
        return sApplication.mActivityHelper;
    }

    private void initApp(){
        Hawk.init(mContext).build();
        Logger.init()
                .logLevel(mLogLevel)
                .methodCount(3);


    }

    public static boolean isApkDebug(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception ignored) {

        }
        return false;
    }



}
