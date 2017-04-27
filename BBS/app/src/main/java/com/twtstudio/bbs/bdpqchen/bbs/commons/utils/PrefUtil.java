package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import com.orhanobut.hawk.Hawk;

/**
 * Created by bdpqchen on 17-4-18.
 */

public final class PrefUtil {

    private static final String IS_JUST_INSTALLED = "is_just_installed";
    private static final String IS_NIGHT_MODE = "is_night_mode";
    private static final String IS_SLIDE_BACK_MODE = "is_slide_back_mode";
    private static final String IS_AUTO_NIGHT_MODE = "is_auto_night_mode";

    public static boolean isJustInstalled(){
        return Hawk.get(IS_JUST_INSTALLED, false);
    }
    public static void setIsJustInstalled(boolean b){
        Hawk.put(IS_JUST_INSTALLED, b);
    }

    public static boolean isNightMode(){
        return Hawk.get(IS_NIGHT_MODE, false);
    }
    public static void setIsNightMode(boolean b){
        Hawk.put(IS_NIGHT_MODE, b);
    }

    public static boolean isSlideBackMode(){
        return Hawk.get(IS_SLIDE_BACK_MODE, true);
    }
    public static void setIsSlideBackMode(boolean b){
        Hawk.put(IS_SLIDE_BACK_MODE, b);
    }

    public static boolean isAutoNightMode(){
        return Hawk.get(IS_AUTO_NIGHT_MODE, false);
    }
    public static void setIsAutoNightMode(boolean b){
        Hawk.put(IS_AUTO_NIGHT_MODE, b);
    }

}
