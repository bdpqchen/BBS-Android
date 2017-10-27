package com.twtstudio.bbs.bdpqchen.bbs.htmltextview;

/**
 * Created by Jesson_Yi on 2016/6/22.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.WindowUtil;

import java.util.HashSet;


public class GlideImageGeter implements Html.ImageGetter {

    private HashSet<GifDrawable> gifDrawables;
    private final Context mContext;
    private final TextView mTextView;
//    private float betterImgScale = 0.65f;
    private float betterImgScale = 1f;

    public GlideImageGeter(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;
        gifDrawables = new HashSet<>();
    }

    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable(mContext);
        final  GenericRequestBuilder load;
        final Target target;
        load = Glide.with(mContext).load(url).asBitmap().fitCenter()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        LogUtil.dd("glide laod onException");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        LogUtil.dd("glide load onResourceReady");

                        return false;
                    }
                });
        target = new BitmapTarget(urlDrawable);

//        load.override(100, 100).into(target);
        load.override(600, 600);
        load.into(target);
        return urlDrawable;
    }

    private class BitmapTarget extends SimpleTarget<Bitmap> {

        private final UrlDrawable urlDrawable;

        public BitmapTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            Drawable drawable = new BitmapDrawable(mContext.getResources(), resource);
            Rect rect = getRect(drawable);
            drawable.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(drawable);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }

    private Rect getRect(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        if (isTooHigh(h) || isTooWide(w)) {
            w = (int) (w * betterImgScale);
            h = (int) (h * betterImgScale);
        }
        return new Rect(0, 0, w, h);
    }

    private boolean isTooWide(int imgWidth) {
        return imgWidth > WindowUtil.getWindowWidth(mContext) / 2;
    }

    private boolean isTooHigh(int imgHigh) {
        return imgHigh > WindowUtil.getWindowHeight(mContext) / 2;
    }


}