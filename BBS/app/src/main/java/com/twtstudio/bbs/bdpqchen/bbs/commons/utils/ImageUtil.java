package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-4.
 */

public final class ImageUtil {

    public static String getAvatarUrl(int uid) {
        return RxDoHttpClient.BASE_URL + "user/" + uid + "/avatar";
    }

    public static void loadIconAsBitmap(Context context, int resourceId, ImageView view) {
        Glide.with(context).load(resourceId).asBitmap().centerCrop().dontAnimate().into(view);
    }

    public static void loadDrawable(Context context, int resource, ImageView view) {
        Glide.with(context)
                .load(resource)
                .crossFade()
                .into(view);
    }

    public static void loadForumCover(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .crossFade()
//                .placeholder(R.drawable.forum_banner_1)
//                .centerCrop()
//                .override(300, 150)
//                .error(R.drawable.forum_banner_1)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void loadFromUrl(Context context, String url, ImageView view) {
        Glide.with(context).load(url).centerCrop().into(view);
    }

    public static void loadAvatarByUid(Context context, int uid, ImageView view) {
        Glide.with(context)
                .load(getAvatarUrl(uid))
                .centerCrop()
                .dontAnimate()
                .into(view);

    }
    public static void loadAvatarByPath(Context context, String path, ImageView view) {
        Glide.with(context)
                .load(path)
                .centerCrop()
                .into(view);
    }

    public static void loadAvatarAsBitmapByUid(Context context, int uid, ImageView view) {
        Glide.with(context).load(getAvatarUrl(uid))
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.avatar_default_left)
                .into(view);
    }
    public static void loadAvatarAsBitmapByUidWithRight(Context context, int author_id, CircleImageView civAvatarPost) {
        Glide.with(context).load(getAvatarUrl(author_id))
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.avatar_default_right)
                .into(civAvatarPost);

    }

    public static void loadMyAvatar(Context context, ImageView civAvatar) {
        loadAvatarByUid(context, PrefUtil.getAuthUid(), civAvatar);
    }

    public static void refreshMyAvatar(Context context, ImageView civAvatar) {
        refreshAvatar(context, PrefUtil.getAuthUid(), civAvatar);
    }

    private static void refreshAvatar(Context context, int uid, ImageView view){
        Glide.with(context)
                .load(getAvatarUrl(uid))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view);
    }

}
