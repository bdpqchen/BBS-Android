package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-4.
 */

public final class ImageUtil {

    private static String getAvatarUrl(int uid){
        return RxDoHttpClient.BASE_URL + "user/" + uid + "/avatar";
    }

    public static void loadAsBitmap(Context context, int resourceId, ImageView view) {
        Glide.with(context).load(resourceId).asBitmap().centerCrop().dontAnimate().into(view);
    }

    public static void loadWithHeight(Context context, int resourceId, ImageView view) {
        Glide.with(context).load(resourceId).centerCrop().dontAnimate().into(view);
    }

    public static void loadFromUrl(Context context, String url, ImageView view) {
        Glide.with(context).load(url).centerCrop().into(view);
    }

    public static void loadAvatarByUid(Context context, int uid, ImageView view) {
        Glide.with(context).load(getAvatarUrl(uid)).centerCrop()
    //               .placeholder(R.drawable.avatar2)
                .into(view);
    }

    public static void loadAvatarAsBitmapByUid(Context context, int uid, ImageView view){
        Glide.with(context).load(getAvatarUrl(uid))
                .asBitmap()
                .centerCrop()
//                .placeholder(R.drawable.avatar2)
                .into(view);
    }

    public static void loadMyAvatar(Context context, ImageView civAvatar) {
//        String url = RxDoHttpClient.BASE_URL + "user/19667/avatar";
        loadAvatarByUid(context, PrefUtil.getAuthUid(), civAvatar);
    }




}
