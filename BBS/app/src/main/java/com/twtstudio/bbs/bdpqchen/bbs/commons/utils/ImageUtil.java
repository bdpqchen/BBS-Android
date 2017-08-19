package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twtstudio.bbs.bdpqchen.bbs.R;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by bdpqchen on 17-5-4.
 */

public final class ImageUtil {

    private final static int radius = 30;
    private final static int myUid = PrefUtil.getAuthUid();


    public static void loadIconAsBitmap(Context context, int resourceId, ImageView view) {
        Glide.with(context).load(resourceId).asBitmap().centerCrop().into(view);
    }

    public static void loadAnonAvatar(Context context, ImageView view) {
        Glide.with(context).load(R.drawable.avatar_anonymous_left).asBitmap().centerCrop().crossFade().into(view);
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
                .into(view);
    }

    public static void loadLoginCover(Context context, int fid, ImageView view) {
        Glide.with(context)
                .load(UrlUtil.getCoverUrl(fid))
                .crossFade()
                .error(R.drawable.cover_login)
//                .placeholder(R.drawable.cover_login)
                .into(view);
    }

    public static void loadAvatarAsBitmapByUid(Context context, int uid, ImageView view) {
        Glide.with(context).load(UrlUtil.getAvatarUrl(uid))
                .asBitmap()
                .centerCrop()
                .crossFade().diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.avatar_default_left)
                .into(view);
    }

    public static void loadAvatarAsBitmapByUidWithRight(Context context, int author_id, CircleImageView civAvatarPost) {
        Glide.with(context).load(UrlUtil.getAvatarUrl(author_id))
                .asBitmap()
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.avatar_default_right)
                .into(civAvatarPost);

    }

    public static void loadAvatarAsBitmapByUidWithLeft(Context context, int author_id, CircleImageView civAvatarPost) {
        Glide.with(context).load(UrlUtil.getAvatarUrl(author_id))
                .asBitmap()
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.avatar_default_left)
                .into(civAvatarPost);
    }

    public static void loadMyAvatar(Context context, ImageView civAvatar) {
        loadAvatarByUid(context, myUid, civAvatar);
    }

    public static void loadAvatarByUid(Context context, int uid, ImageView view) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view);

    }

    public static void loadMyBg(Context context, ImageView imageView) {
        loadBgByUid(context, myUid, imageView);
    }

    public static void loadBgByUid(Context context, int uid, ImageView view) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .bitmapTransform(new BlurTransformation(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view);
    }

    public static void refreshMyBg(Context context, ImageView view) {
        refreshMyBg(context, myUid, view);
    }

    private static void refreshMyBg(Context context, int myUid, ImageView view) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(myUid))
                .skipMemoryCache(true)
                .bitmapTransform(new BlurTransformation(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view);
    }

    public static void refreshMyAvatar(Context context, ImageView civAvatar) {
        refreshAvatar(context, PrefUtil.getAuthUid(), civAvatar);
    }

    private static void refreshAvatar(Context context, int uid, ImageView view) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view);
    }

    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public static void clearDiskCache(Context context) {
        new Thread(() -> {
            Glide.get(context).clearDiskCache();
        }).start();
    }

}
