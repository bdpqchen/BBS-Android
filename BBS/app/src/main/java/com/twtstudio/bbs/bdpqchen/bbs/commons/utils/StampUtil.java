package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by bdpqchen on 17-5-10.
 */

public final class StampUtil {

    private static final int diff = 28800;

    public static int getDaysFromCreateToNow(int createTimeStamp) {
        if (createTimeStamp < 12721691) {
            return 0;
        }
        //由于创建时间要多8小时
        createTimeStamp -= diff;
        int currentTime = (int) (System.currentTimeMillis() / 1000);
//        LogUtil.d("current time", String.valueOf(currentTime));

        int days = (currentTime - createTimeStamp) / 86400;
//        LogUtil.d(days);
        return days + 1;    //不能从零天开始计算
    }

    private static String convert(String formatMode, int oldStamp) {
        oldStamp -= diff;
        long longTime = Long.parseLong(oldStamp + "000");
        SimpleDateFormat format = new SimpleDateFormat(formatMode);
        return format.format(new Date(longTime));
    }

    public static String getDatetimeByStamp(int postTime) {
        String datetimeMode = "yyyy-MM-dd HH:mm";
        return convert(datetimeMode, postTime + diff);
    }
    public static String getTimeFromNow(long time) {
        String datetimeMode = "MM-dd HH:mm:ss";
        return convert(datetimeMode, (int) (time + diff));
    }

    public static String getDateByStamp(int t_create) {
        String dateMode = "yyyy-MM-dd";
        return convert(dateMode, t_create);
    }

    public static String TimeStamp2Date(long timestampString) {
        Long timestamp = timestampString * 1000;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        return date;
    }

  /*  public static String getTimeFromNow(long date) {
//        date -= diff;
        Calendar calendar = Calendar.getInstance();
//        Calendar calendar = new GregorianCalendar();

//        LogUtil.dd("calendar");

        int years = calendar.get(Calendar.YEAR);
        int months = calendar.get(Calendar.MONTH);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        LogUtil.dd("years", String.valueOf(years));
        LogUtil.dd("months", String.valueOf(months));
        LogUtil.dd("days", String.valueOf(days));
        LogUtil.dd("hours", String.valueOf(hours));

        calendar.setTimeInMillis(date * 1000);

        LogUtil.dd("date", String.valueOf(date));

        years -= calendar.get(Calendar.YEAR);
        months -= calendar.get(Calendar.MONTH);
        days -= calendar.get(Calendar.DAY_OF_MONTH);
        hours -= calendar.get(Calendar.HOUR_OF_DAY);
        minutes -= calendar.get(Calendar.MINUTE);
        seconds -= calendar.get(Calendar.SECOND);

        if (years > 0) {
            return years + " " + "年前";
        } else if (months > 0) {
            return months + " " + "月前";
        } else if (days > 0) {
            return days + " " + "天前";
        } else if (hours > 0) {
            return hours + " " + "小时前";
        } else if (minutes > 0) {
            return minutes + " " + "分钟前";
        } else if (seconds > 0) {
            return seconds + " " + "秒前";
        } else {
            return "刚刚";
        }
    }
*/
}
