package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import com.orhanobut.logger.Logger;

/**
 * Created by bdpqchen on 17-4-18.
 */

public final class LogUtils{

    public static void v(String s){
        Logger.v(s);
    }
    public static void v(String t, Object o){
//        Logger.t(t).v(o);
        Logger.v(t, o);
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
    public static void i(String t, Object o){
        Logger.i(t, o);
    }

    public static void w(String s, Object o){
        Logger.w(s, o);
    }

    public static void e(String s, Object o){
        Logger.e(s, o);
        Logger.i("sss");
    }

}