package com.twtstudio.bbs.bdpqchen.bbs.main;

import android.util.Log;

/**
 * Created by zhangyulong on 5/15/17.
 */

public class TimeUtils {
    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * @param t   时间戳
     * @return
     */
    public static String getStandardDate(long t) {

        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis()-(t*1000)+(8*60*60*1000);
        Log.d("currentTime",Long.toString(time));
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

}
