package com.twtstudio.bbs.bdpqchen.bbs;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.HandlerThread;
import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.oubowu.slideback.ActivityHelper;
import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.AppComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.component.DaggerAppComponent;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.AppModule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        if (!BuildConfig.DEBUG) {
            PgyCrashManager.register(this);
        }
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        CrashReport.initCrashReport(context, "d0cd942fc3", true, strategy);
//        CrashReport.initCrashReport(getApplicationContext(), "", true);

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

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


}
