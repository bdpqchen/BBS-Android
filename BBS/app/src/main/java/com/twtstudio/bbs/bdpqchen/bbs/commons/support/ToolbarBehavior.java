package com.twtstudio.bbs.bdpqchen.bbs.commons.support;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

/**
 * Created by bdpqchen on 17-7-6.
 */

public class ToolbarBehavior extends CoordinatorLayout.Behavior<Toolbar> {


    private static final String TAG = "ToolbarAlphaBehavior";
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context context;
    private int consumed = 0, unconsumed = 0;

    public ToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
//        LogUtil.dd(TAG, String.valueOf(nestedScrollAxes));
        return true;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar toolbar, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        int alpha = toolbar.getBackground().getAlpha();
        if (dyConsumed > 50) {
            alpha = 255;
        }
        if (dyConsumed < -50) {
            alpha = 0;
        }
        if (dyConsumed > 0 && dyConsumed < 20) {
            dyConsumed += 3;
        } else if (dyConsumed > -20 && dyConsumed < 0) {
            dyConsumed -= 3;
        }
        alpha += dyConsumed;
        if (alpha > 255) {
            alpha = 255;
        }
        if (alpha < 0) {
            alpha = 0;
        }
        toolbar.getBackground().mutate().setAlpha(alpha);
    }
}