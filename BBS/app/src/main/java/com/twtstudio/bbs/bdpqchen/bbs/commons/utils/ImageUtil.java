package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

/**
 * Created by bdpqchen on 17-5-4.
 */

public final class ImageUtil {

    private static String getAvatarUrl(int uid){
        return RxDoHttpClient.BASE_URL + "user/" + uid + "/avatar";
    }
    public static void loadIconAsBitmap(Context context, int resourceId, ImageView view) {
        Glide.with(context).load(resourceId).asBitmap().centerCrop().dontAnimate().into(view);
    }

    public static void loadForumCover(Context context, String url, ImageView view){
        Glide.with(context)
                .load(url)
                .crossFade()
                .placeholder(R.drawable.forum_banner_1)
                .centerCrop()
//                .override(300, 150)
//                .error(R.drawable.forum_banner_1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void loadFromUrl(Context context, String url, ImageView view) {
        Glide.with(context).load(url).centerCrop().into(view);
    }

    public static void loadAvatarByUid(Context context, int uid, ImageView view) {
        Glide.with(context)
                .load(getAvatarUrl(uid))
                .centerCrop()
                   .placeholder(R.drawable.avatar2)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);

    }

    public static void loadAvatarAsBitmapByUid(Context context, int uid, ImageView view){
        Glide.with(context).load(getAvatarUrl(uid))
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.avatar2)
                .into(view);
    }

    public static void loadMyAvatar(Context context, ImageView civAvatar) {
//        String url = RxDoHttpClient.BASE_URL + "user/19667/avatar";
        loadAvatarByUid(context, PrefUtil.getAuthUid(), civAvatar);
    }

}
