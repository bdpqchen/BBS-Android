package com.twtstudio.bbs.bdpqchen.bbs.commons.support;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

/**
 * Created by bdpqchen on 17-3-29.
 */

public class FABScrollBehavior extends FloatingActionButton.Behavior {

    public FABScrollBehavior(Context context, AttributeSet attributeSet){
        super();
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        LogUtil.dd("onNestedScroll");
//        LogUtil.dd("dyConsumed", String.valueOf(dyConsumed));
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
//            LogUtil.dd("clide hide");
            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    fab.setVisibility(View.INVISIBLE);
                }
            });

        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE){
//            LogUtil.dd("clide show");
            child.show();
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
//        LogUtil.dd("onStartNestedScroll");
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

}