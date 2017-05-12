package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by bdpqchen on 17-5-11.
 */

public final class WindowUtil  {


    public static int getWindowWidth(Activity activity){
//        Point point = new Point();
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //不含虚拟按键
//        windowManager.getDefaultDisplay().getSize(point);
//        int width = point.x;
        int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        return screenWidth;
    }



//    public static int getScreenHeight

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
