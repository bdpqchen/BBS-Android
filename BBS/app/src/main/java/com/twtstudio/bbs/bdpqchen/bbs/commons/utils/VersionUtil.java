package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;


import android.os.Build;

/**
 * Created by bdpqchen on 17-9-16.
 */

public final class VersionUtil {

    private final static int sdk = Build.VERSION.SDK_INT;

    public static boolean isLollipop() {
        return sdk == Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isKitkat(){
        return sdk == Build.VERSION_CODES.KITKAT;
    }

    //equal and above
    public static boolean eaLollipop(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean eaMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
