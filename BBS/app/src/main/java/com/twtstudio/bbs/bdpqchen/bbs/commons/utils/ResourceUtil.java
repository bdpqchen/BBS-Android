package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.twtstudio.bbs.bdpqchen.bbs.R;

/**
 * Created by bdpqchen on 17-4-19.
 */

public final class ResourceUtil {

    public static int getColor(Context context, int resourceId){
        return ContextCompat.getColor(context, resourceId);
    }

    public static int getColor(Activity activity, int resourceId){
        return ContextCompat.getColor(activity, resourceId);
    }

    public static Drawable getDrawable(Activity activity, int resourceId){
        return ContextCompat.getDrawable(activity, resourceId);
    }

    public static Bitmap getBitmapFromResource(Activity activity, int resourceId){
        return BitmapFactory.decodeResource(activity.getResources(), resourceId);
    }

}
