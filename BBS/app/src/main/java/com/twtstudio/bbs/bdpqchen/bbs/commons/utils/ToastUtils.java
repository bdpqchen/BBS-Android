package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.twtstudio.bbs.bdpqchen.bbs.R;

/**
 * Created by bdpqchen on 17-4-21.
 */

public final class ToastUtils {

    private static final int NORMAL_BG        = 0xFF0BC4C4;
    private static final int NOTICE_BG        = 0xFFe2a712;
    private static final int ERROR_BG         = 0xFFf2381f;


    public static void normal(Activity act, String m){
        normal(act, m, false);
    }

    public static void normal(Activity act, String m, boolean isLongTime){
        show(act, m, isLongTime(isLongTime), NORMAL_BG);
    }

    public static void notice(Activity act, String m){
        notice(act, m, false);
    }

    public static void notice(Activity act, String m, boolean isLongTime){
        show(act, m, isLongTime(isLongTime), NOTICE_BG);
    }

    public static void error(Activity act, String m){
        error(act, m, false);
    }

    public static void error(Activity act, String m, boolean b) {
        show(act, m, isLongTime(b), ERROR_BG);
    }


    private static void show(Activity act, String m, int duration, int color){

        final TSnackbar snackBar = TSnackbar.make(act.findViewById(android.R.id.content),
                m, duration);

        View snackBarView = snackBar.getView();
        snackBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        //设定snackBar 最小高度
        TypedValue tv = new TypedValue();
        if (act.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true))
        {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, act.getResources().getDisplayMetrics());
            snackBarView.setMinimumHeight(actionBarHeight);
        }

        snackBarView.setBackgroundColor(color);
        TextView text = (TextView) snackBarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        text.setTextSize(16);
        text.setTextColor(Color.WHITE);
        //如果存在action, 将注释这一行
        text.setGravity(Gravity.CENTER);
        snackBar.show();
    }

    private static int isLongTime(boolean isL){
        return isL ? TSnackbar.LENGTH_LONG : TSnackbar.LENGTH_SHORT;
    }

}
