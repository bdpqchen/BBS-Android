package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

/**
 * Created by bdpqchen on 17-5-10.
 */

public final class StampUtil {

    public static int getDaysFromCreateToNow(int createTimeStamp){
        //由于创建时间要多8小时
        createTimeStamp -= 28800;
        int currentTime = (int) (System.currentTimeMillis()/1000);
        LogUtil.d("current time", String.valueOf(currentTime));

        int days = (currentTime - createTimeStamp) / 86400;
        LogUtil.d(days);
        return days + 1;    //不能从零天开始计算
    }

}
