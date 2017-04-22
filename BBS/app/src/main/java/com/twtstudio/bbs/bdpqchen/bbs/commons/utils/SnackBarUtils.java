package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.isanwenyu.topsnackbar.TopSnackbar;
import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.R;

/**
 * Created by bdpqchen on 17-4-21.
 */

public final class SnackBarUtils {

    private static final int NORMAL_BG = 0xFF0BC4C4;
    private static final int NOTICE_BG = 0xFFe2a712;
    private static final int ERROR_BG = 0xFFf2381f;

    private static boolean sIsShowing = false;
    private static int sOldColor;

    public static void normal(Activity act, String m) {
        normal(act, m, false);
    }

    public static void normal(Activity act, String m, boolean isLongTime) {
        if (!sIsShowing){
            sIsShowing = true;
            show(act, m, isLongTime(isLongTime), NORMAL_BG);
        }
    }

    public static void notice(Activity act, String m) {
        notice(act, m, false);
    }

    public static void notice(Activity act, String m, boolean isLongTime) {
        if (!sIsShowing){
            sIsShowing = true;
            show(act, m, isLongTime(isLongTime), NOTICE_BG);
        }
    }

    public static void error(Activity act, String m) {
        error(act, m, false);
    }

    public static void error(Activity act, String m, boolean b) {
        if (!sIsShowing) {
            sIsShowing = true;
            show(act, m, isLongTime(b), ERROR_BG);
        }
    }

    private static void show(final Activity act, String m, int duration, final int color) {
        FrameLayout view = (FrameLayout) act.findViewById(android.R.id.content);
        final TSnackbar snackBar = TSnackbar.make(view, m, duration);
        View snackBarView = snackBar.getView();
        sOldColor = ResourceUtils.getColor(act, R.color.colorPrimaryDark);
        //多次调用会出现sOldColor=上次的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sOldColor = act.getWindow().getStatusBarColor();
        }

        snackBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //等待动画时间
                        StatusBarUtil.setColor(act, sOldColor, 0);
                    }
                }, 250);
            }
        });
        //设定snackBar 最小高度
        TypedValue tv = new TypedValue();
        if (act.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, act.getResources().getDisplayMetrics());
            snackBarView.setMinimumHeight(actionBarHeight);
        }
        snackBarView.setBackgroundColor(color);
        StatusBarUtil.setColor(act, color, 0);

        TextView text = (TextView) snackBarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        text.setTextSize(16);
        text.setTextColor(Color.WHITE);
        //如果存在action, 将注释这一行
        text.setGravity(Gravity.CENTER);
        snackBar.setCallback(new TSnackbar.Callback() {
            @Override
            public void onDismissed(TSnackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                sIsShowing = false;
                if (snackbar == snackBar){
                    StatusBarUtil.setColor(act, sOldColor, 0);
                }else{
                    StatusBarUtil.setColor(act, color, 0);
                }
            }
        });
        snackBar.show();
    }

    private static int isLongTime(boolean isL) {
        return isL ? TSnackbar.LENGTH_LONG : TSnackbar.LENGTH_SHORT;
    }

}
