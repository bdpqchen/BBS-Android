package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import java.util.Calendar;

/**
 * Created by bdpqchen on 17-5-10.
 */

public final class StampUtil {

    private static final int diff = 0;

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

    public static String getDatetimeByStamp(int postTime) {
        Calendar calendar = Calendar.getInstance();
        int[] dateNow = getCalendar(calendar);
        Long dateLong = Long.valueOf((postTime + "000"));
        calendar.setTimeInMillis(dateLong);
        int[] date = getCalendar(calendar);
        String result = "";
        if (date[0] < dateNow[0]) {
            result += date[0] + "年" + date[1] + "月" + date[2] + "号 ";
        } else {
            if (date[1] == dateNow[1] && date[2] == dateNow[2]) {
                result = "今天 ";
            } else {
                result = date[1] + "月" + date[2] + "号 ";
            }
        }
        if (date[3] == 0) {
            result += "0";
        }
        result += date[3] + ":";
        if (date[4] < 10) {
            result += "0";
        }
        result += date[4];
//        LogUtil.dd("result time", result);
        return result;
    }

    private static int[] getCalendar(Calendar calendar) {
        int[] date = new int[6];
        date[0] = calendar.get(Calendar.YEAR);
        date[1] = calendar.get(Calendar.MONTH) + 1;
        date[2] = calendar.get(Calendar.DAY_OF_MONTH);
        date[3] = calendar.get(Calendar.HOUR_OF_DAY);
        date[4] = calendar.get(Calendar.MINUTE);
        date[5] = calendar.get(Calendar.SECOND);
        return date;
    }

    public static String getTimeFromNow(int createTime, int replyTime) {
        if (IsUtil.INSTANCE.is0(replyTime) || replyTime == createTime) {
            return "发布于 " + getTimeFromNow(createTime);
        } else {
            return getTimeFromNow(replyTime) + "有新动态";
        }
    }

    public static String getTimeFromNow(int date) {
        Calendar calendar = Calendar.getInstance();
        int years = calendar.get(Calendar.YEAR);
        int months = calendar.get(Calendar.MONTH);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        Long dateLong = Long.valueOf((date + "000"));
        calendar.setTimeInMillis(dateLong);
        years -= calendar.get(Calendar.YEAR);
        months -= calendar.get(Calendar.MONTH);
        days -= calendar.get(Calendar.DAY_OF_MONTH);
        hours -= calendar.get(Calendar.HOUR_OF_DAY);
        minutes -= calendar.get(Calendar.MINUTE);
        seconds -= calendar.get(Calendar.SECOND);
        if (years == 1 && months >= 0 || years > 1) {
            return years + "年前";
        } else if (months == 1 && days >= 0 || months > 1) {
            return months + "月前";
        } else if (days > 1) {
            return days + "天前";
        } else if (days == 1) {
            if (hours < 0) {
                hours += 24;
            }
            return hours + "小时前";
        } else if (hours > 1) {
            return hours + "小时前";
        } else if (hours == 1) {
            if (minutes < 0) {
                minutes += 60;
            } else {
                return hours + "小时前";
            }
            return minutes + "分钟前";
        } else if (minutes > 1) {
            return minutes + "分钟前";
        } else if (minutes == 1) {
            if (seconds < 0) {
                seconds += 60;
            }
            return seconds + "秒前";
        } else if (seconds > 0) {
            return seconds + " " + "秒前";
        } else {
            return "刚刚";
        }
    }
}
