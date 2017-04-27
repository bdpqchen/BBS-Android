package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by bdpqchen on 17-4-18.
 */

public final class LogUtil {

    public static boolean mIsLogable = true;

    public static void v(String s){
        Logger.v(s);
    }

    public static void d(Object o){
        Logger.d(o);
    }
    public static void d(String t, Object o){
        Logger.t(t).d(o);
    }

    public static void i(String s){
        Logger.i(s);
    }
   /* public static void i(String t, Object o){
        Logger.i(t, o);
    }*/

    public static void w(String s, Object o){
        Logger.w(s, o);
    }
   /* public static void e(String s, Object o){
        Logger.e(s, o);
        Logger.i("sss");
    }*/

   public static void vv(@Nullable String t, String m){
       if (mIsLogable)
           Log.v(t, m);
   }

   public static void dd(@Nullable String t, String m){
       if (mIsLogable)
           Log.d(t, m);
   }

    public static void ii(@Nullable String t, String m){
        if (mIsLogable)
            Log.i(t, m);
    }

    public static void ww(@Nullable String t, String m){
        if (mIsLogable)
            Log.w(t, m);
    }

    public static void ee(@Nullable String t, String m){
        if (mIsLogable)
            Log.e(t, m);
    }




}