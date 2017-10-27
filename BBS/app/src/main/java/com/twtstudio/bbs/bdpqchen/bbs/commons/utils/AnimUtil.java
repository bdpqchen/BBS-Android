package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by bdpqchen on 17-9-25.
 */

public final class AnimUtil {

    public static void revealFromCenter(Context context, ViewGroup viewRoot, View view, int rootViewBgColor) {
        revealByXY(context, viewRoot, (int) view.getX(), (int) view.getY(), rootViewBgColor);
    }

    public static void revealByXY(Context context, ViewGroup viewRoot, int x, int y, int rootViewBgColor) {
        if (can()) return;
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(context, rootViewBgColor));
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }


    private static boolean can() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

}
