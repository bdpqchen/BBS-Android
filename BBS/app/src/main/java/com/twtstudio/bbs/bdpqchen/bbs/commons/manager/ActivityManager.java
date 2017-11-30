package com.twtstudio.bbs.bdpqchen.bbs.commons.manager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.VersionUtil;

import java.util.Stack;

/**
 * Created by bdpqchen on 17-4-17.
 */

public final class ActivityManager {
    private static Stack<AppCompatActivity> mActivityStack;

    private static ActivityManager mInstance;

    private ActivityManager() {
    }

    public static ActivityManager getActivityManager() {
        if (mInstance == null) {
            mInstance = new ActivityManager();
        }
        return mInstance;
    }

    public void recreateAllActivity(Class<?> cls) {
        if (mActivityStack != null) {
            for (AppCompatActivity activity : mActivityStack) {
                if (activity.getClass() != cls) {
                    activity.recreate();
                }
            }
        }
    }

    public void addActivity(AppCompatActivity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    //结束当前的activity
    public void finishCurrentActivity() {
        AppCompatActivity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    public AppCompatActivity getCurrentActivity() {
        return mActivityStack.lastElement();
    }

    public void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            if (VersionUtil.eaLollipop()) {
                activity.supportFinishAfterTransition();
            } else {
                activity.finish();
            }
        } else {
            Logger.d("You want to remove the activity, but it is null");
        }
    }

    //结束指定名称的activity
    public void finishNamedActivity(Class<?> cls) {
        for (AppCompatActivity activity : mActivityStack) {
            if (activity.getClass() == cls) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        int i = 0;
        while (!mActivityStack.empty()) {
            finishActivity(mActivityStack.firstElement());
            i++;
        }
        Log.d("ActivityManager", "finished " + i + " activity");
    }

    public void appExit(Context context) {
        finishAllActivity();
        System.exit(0);
    }


}
