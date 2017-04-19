package com.twtstudio.bbs.bdpqchen.bbs;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by bdpqchen on 17-4-17.
 */

public class App extends Application {

    private Context mContext;
    private LogLevel mLogLevel = LogLevel.FULL;

    private static App sMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        if (!isApkDebug(mContext)){
            mLogLevel = LogLevel.NONE;
        }

        initApp();

        sMyApplication = this;

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
