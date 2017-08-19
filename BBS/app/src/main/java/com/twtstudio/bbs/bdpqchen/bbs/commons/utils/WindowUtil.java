package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by bdpqchen on 17-5-11.
 */

public final class WindowUtil  {

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    public static int getWindowWidth(Activity activity){
//        Point point = new Point();
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //不含虚拟按键
//        windowManager.getDefaultDisplay().getSize(point);
//        int width = point.x;
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getWindowHeight(Context context){
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //不含虚拟按键
        windowManager.getDefaultDisplay().getSize(point);
        int height = point.y;
        return height;
    }

    public static int getWindowWidth(Context context){
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //不含虚拟按键
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    private void measure(Context context) {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //不含虚拟按键
        windowManager.getDefaultDisplay().getSize(point);
        //包含虚拟按键
        //windowManager.getDefaultDisplay().getRealSize(point);
        //屏幕宽度
        int height = point.y;
        //屏幕高度
        int width = point.x;
    }

}
