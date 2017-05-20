package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bdpqchen on 17-5-10.
 */

public final class StampUtil {

    private static final int diff = 28800;

    public static int getDaysFromCreateToNow(int createTimeStamp){
        //由于创建时间要多8小时
        createTimeStamp -= diff;
        int currentTime = (int) (System.currentTimeMillis()/1000);
//        LogUtil.d("current time", String.valueOf(currentTime));

        int days = (currentTime - createTimeStamp) / 86400;
//        LogUtil.d(days);
        return days + 1;    //不能从零天开始计算
    }

    public static String getDatetimeByStamp(int postTime){
        postTime -= diff;
        long longTime = Long.parseLong(postTime + "000");
        String datetimeMode = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(datetimeMode);
        String datetime = format.format(new Date(longTime));
        return datetime;
    }
}
