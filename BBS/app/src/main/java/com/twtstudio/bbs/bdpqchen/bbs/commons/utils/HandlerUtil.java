package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.os.Handler;

/**
 * Created by bdpqchen on 17-5-3.
 */

public final class HandlerUtil {

    public static void postDelay(Runnable runnable, int delay){
        // TODO: 17-5-3 完善handler工具类 search in google.com
        new Handler().postDelayed(runnable, delay);

    }


}
