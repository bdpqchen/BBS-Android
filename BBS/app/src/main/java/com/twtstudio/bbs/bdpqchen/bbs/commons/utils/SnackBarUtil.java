package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.view.reminder.Reminder;

/**
 * Created by bdpqchen on 17-4-21.
 */

public final class SnackBarUtil {

    private static final int NORMAL_BG = R.color.reminderBgNormal;
    private static final int NOTICE_BG = R.color.reminderBgNotice;
    private static final int ERROR_BG = R.color.reminderBgErrror;

    public static void normal(Activity act, String m) {
        normal(act, m, false);
    }

    public static void normal(Activity act, String m, boolean isLongTime) {
        show(act, m, isLongTime(isLongTime), NORMAL_BG);
    }

    public static void normal(Activity act, String m, String actionTitle, Reminder.OnActionClickListener listener) {
        show(act, m, isLongTime(true), NORMAL_BG, actionTitle, listener);
    }

    public static void notice(Activity act, String m) {
        notice(act, m, false);
    }

    public static void notice(Activity act, String m, boolean isLongTime) {
        int duration = isLongTime ? 1500 : 2000;
        show(act, m, duration, NOTICE_BG);
    }

    public static void notice(Activity act, String m, String actionTitle, Reminder.OnActionClickListener listener) {
        show(act, m, isLongTime(true), NOTICE_BG, actionTitle, listener);
    }

    public static void error(Activity act, String m) {
        error(act, m, false);
    }

    public static void error(Activity act, String m, boolean isLongTime) {
        show(act, m, isLongTime(isLongTime), ERROR_BG);
    }

    public static void error(Activity act, String m, String actionTitle, Reminder.OnActionClickListener listener) {
        show(act, m, isLongTime(true), ERROR_BG, actionTitle, listener);
    }

    private static void show(final Activity act, String m, int duration, final int color) {
        show(act, m, duration, color, "", null);
    }

    private static void show(final Activity act, String m, int duration, final int color, String actionTitle, Reminder.OnActionClickListener listener) {
        new Reminder.Builder(act)
                .setMessage(m)
                .setDuration(duration)
                .setBackgroundColor(color)
                .setAction(actionTitle, "撤销", listener)
                .show();
    }

    private static int isLongTime(boolean isL) {
        return isL ? 3000 : 1000;
    }

}
