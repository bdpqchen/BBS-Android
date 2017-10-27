package com.twtstudio.bbs.bdpqchen.bbs.commons.view.reminder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by bdpqchen on 17-10-7.
 */

public final class Utils {

    public static boolean notNull(int i) {
        return i != 0;
    }

    public static boolean notNull(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static boolean notNull(Object object) {
        return object != null;
    }

    public static int getMinHeight(Context context){
        return getActionBarHeight(context) + getStatusBarHeight(context);
    }

    private static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int height = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
             height = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        Log.d("height action bar", String.valueOf(height));
        return height;
    }
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        Log.d("height status bar", String.valueOf(statusBarHeight));
        return statusBarHeight;
    }

    public static int getColor(Context context, int color){
        return ContextCompat.getColor(context, color);
    }


}
