package com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.R;

/**
 * Created by Jesson_Yi on 2016/6/23.
 */
public class UrlDrawable extends BitmapDrawable implements Drawable.Callback{
    private Drawable drawable ;

    @SuppressWarnings("deprecation")
    public UrlDrawable() {
    }

    public UrlDrawable(Context context){
//        this.drawable = ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.avatar_anonymous_left);
        //todo 占位图
    }

    @Override
    public void draw(Canvas canvas) {
        if (drawable != null)
            drawable.draw(canvas);
    }

    public Drawable getDrawable() {
        return drawable;
    }
    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }
    @Override
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
//        drawable.setBounds();
    }
}