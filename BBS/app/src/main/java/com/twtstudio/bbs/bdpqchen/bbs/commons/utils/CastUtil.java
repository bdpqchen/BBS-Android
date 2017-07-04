package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

/**
 * Created by bdpqchen on 17-6-7.
 */

public final class CastUtil {

    public static boolean cast2boolean(Object newValue) {
        return newValue instanceof Boolean && (boolean) newValue;
    }

    public static int cast2int(Object obj){
        if (obj instanceof Integer){
            return (int) obj;
        }else{
            return 0;
        }
    }

    public static int parse2int(String s){
        int casted = 2147483647;
        try {
            casted = Integer.parseInt(s);
        } catch (NumberFormatException ignored){
        }
        return casted;
    }


}
