package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

/**
 * Created by bdpqchen on 17-5-4.
 */

public final class ImageUtil  {

    public static final String AVATAR_URL = RxDoHttpClient.BASE_URL + "/user/%d/avatar";

    public static final String DEFAULT_AVATAR_URL = RxDoHttpClient.BASE_URL + "/user/" + PrefUtil.getAuthUid() + "avatar";

    public static void load(Context context, int resourceId, ImageView view){
        Glide.with(context).load(resourceId).asBitmap().centerCrop().dontAnimate().into(view);
    }

    public static void loadWithHeight(Context context, int resourceId, ImageView view){
        Glide.with(context).load(resourceId).centerCrop().dontAnimate().into(view);
    }

    public static void loadFromUrl(Context context, String url, ImageView view){
        Glide.with(context).load(url).centerCrop().into(view);
    }

    public static void loadAvatar(Context context, ImageView view){
        Glide.with(context).load(DEFAULT_AVATAR_URL).centerCrop().placeholder(R.drawable.avatar2).into(view);
    }

    public static void loadAvatarById(Context context, int id, ImageView view){
        Glide.with(context).load(String.format(AVATAR_URL, id)).centerCrop().into(view);
    }


/*
    public static void load(Context context, int resourceId, ImageView view){

    }
*/



}
