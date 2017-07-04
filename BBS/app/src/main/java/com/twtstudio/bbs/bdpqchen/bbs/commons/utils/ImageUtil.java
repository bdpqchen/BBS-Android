package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;

/**
 * Created by bdpqchen on 17-5-4.
 */

public final class ImageUtil {

    private static int radius = 30;

    public static String getAvatarUrl(int uid) {
        return RxDoHttpClient.BASE_URL + "user/" + uid + "/avatar";
    }

    public static String getCoverUri(int fid) {
        return RxDoHttpClient.BASE_URL + "forum/" + fid + "/cover";
    }

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
                .load(getCoverUri(fid))
                .crossFade()
                .error(R.drawable.cover_login)
//                .placeholder(R.drawable.cover_login)
                .into(view);
    }

    public static void loadAvatarAsBitmapByUid(Context context, int uid, ImageView view) {
        Glide.with(context).load(getAvatarUrl(uid))
                .asBitmap()
                .centerCrop()
                .crossFade()
                .error(R.drawable.avatar_default_left)
                .into(view);
    }

    public static void loadAvatarAsBitmapByUidWithRight(Context context, int author_id, CircleImageView civAvatarPost) {
        Glide.with(context).load(getAvatarUrl(author_id))
                .asBitmap()
                .centerCrop()
                .crossFade()
                .error(R.drawable.avatar_default_right)
                .into(civAvatarPost);

    }

    public static void loadAvatarAsBitmapByUidWithLeft(Context context, int author_id, CircleImageView civAvatarPost) {
        Glide.with(context).load(getAvatarUrl(author_id))
                .asBitmap()
                .centerCrop()
                .crossFade()
                .error(R.drawable.avatar_default_left)
                .into(civAvatarPost);
    }

    public static void loadMyAvatar(Context context, ImageView civAvatar) {
        loadAvatarByUid(context, PrefUtil.getAuthUid(), civAvatar);
    }

    public static void loadAvatarByUid(Context context, int uid, ImageView view) {
        Glide.with(context)
                .load(getAvatarUrl(uid))
                .centerCrop()
                .crossFade()
                .into(view);

    }

    public static void loadMyBg(Context context, ImageView imageView) {
        loadBgByUid(context, PrefUtil.getAuthUid(), imageView);
    }

    public static void loadBgByUid(Context context, int uid, ImageView view) {
        DrawableRequestBuilder<Integer> thumbnail = Glide.with(context)
                .load(R.drawable.cover_login)
                .bitmapTransform(new BlurTransformation(context, radius));
        Glide.with(context)
                .load(getAvatarUrl(uid))
                .bitmapTransform(new BlurTransformation(context, radius),
                        new ColorFilterTransformation(context, R.color.colorInfoBg))
                .thumbnail(thumbnail)
                .crossFade()
                .into(view);
    }

    public static void refreshMyAvatar(Context context, ImageView civAvatar) {
        refreshAvatar(context, PrefUtil.getAuthUid(), civAvatar);
    }

    private static void refreshAvatar(Context context, int uid, ImageView view) {
        Glide.with(context)
                .load(getAvatarUrl(uid))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view);
    }

}
