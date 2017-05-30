package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.support.v7.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bdpqchen on 17-5-10.
 */

public final class StampUtil {

    private static final int diff = 0;

    public static int getDaysFromCreateToNow(int createTimeStamp){
        if (createTimeStamp < 12721691){
            return 0;
        }
        //由于创建时间要多8小时
        createTimeStamp -= diff;
        int currentTime = (int) (System.currentTimeMillis()/1000);
//        LogUtil.d("current time", String.valueOf(currentTime));

        int days = (currentTime - createTimeStamp) / 86400;
//        LogUtil.d(days);
        return days + 1;    //不能从零天开始计算
    }

    private static String convert(String formatMode, int oldStamp){

        oldStamp -= diff;
        long longTime = Long.parseLong(oldStamp + "000");
        SimpleDateFormat format = new SimpleDateFormat(formatMode);
        return format.format(new Date(longTime));
    }

    public static String getDatetimeByStamp(int postTime){
        String datetimeMode = "yyyy-MM-dd HH:mm";
        return convert(datetimeMode, postTime + diff);
    }

    public static String getDateByStamp(int t_create) {
        String dateMode = "yyyy-MM-dd";
        return convert(dateMode, t_create);
    }

    public static String TimeStamp2Date(long timestampString){
        Long timestamp = timestampString*1000;
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        return date;
    }
}
