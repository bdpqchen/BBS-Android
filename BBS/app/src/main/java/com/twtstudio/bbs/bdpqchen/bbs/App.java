package com.twtstudio.bbs.bdpqchen.bbs;

import android.app.Application;
import android.content.Context;
import android.os.HandlerThread;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.oubowu.slideback.ActivityHelper;
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

        }
        //bugly配置
        Context context = getApplicationContext();
        String packageName = context.getPackageName();
        String processName = getProcessName(android.os.Process.myPid());
       /* CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        Beta.smallIconId = R.mipmap.ic_launcher_bbs;
        if (!BuildConfig.DEBUG){
            Bugly.init(context, BuildConfig.ID_BUGLY, BuildConfig.DEBUG);
        }*/

        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        initLogUtils();
        initSlideBack();
        HandlerThread workerThread = new HandlerThread("global_worker_thread");
        workerThread.start();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
//        Beta.installTinker();
    }
/*

    private Tracker tracker;
    public synchronized Tracker getTracker() {
        if (tracker == null) tracker = Piwik.getInstance(this).newTracker(new TrackerConfig("https://elf.twtstudio.com", 13, "Android"));
        return tracker;
    }
*/

    private void initLogUtils() {
        if (BuildConfig.DEBUG) {
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
