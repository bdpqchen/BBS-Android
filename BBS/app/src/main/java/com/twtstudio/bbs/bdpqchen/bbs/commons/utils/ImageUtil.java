package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by bdpqchen on 17-5-4.
 */

public final class ImageUtil  {

    public static void load(Context context, int resourceId, ImageView view){
        Glide.with(context).load(resourceId).asBitmap().centerCrop().dontAnimate().into(view);
    }

}
