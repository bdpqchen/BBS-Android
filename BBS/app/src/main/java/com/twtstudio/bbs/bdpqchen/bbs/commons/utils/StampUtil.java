package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bdpqchen on 17-5-10.
 */

public final class StampUtil {

    public static int getDaysFromCreateToNow(int createTimeStamp){
        //由于创建时间要多8小时
        createTimeStamp -= 28800;
        int currentTime = (int) (System.currentTimeMillis()/1000);
//        LogUtil.d("current time", String.valueOf(currentTime));

        int days = (currentTime - createTimeStamp) / 86400;
//        LogUtil.d(days);
        return days + 1;    //不能从零天开始计算
    }

    private static final DateFormat dateFormat = new SimpleDateFormat();

    public static String formatDateFromStamp(int stamp) {
        return dateFormat.format(new Date(stamp));
    }

}
