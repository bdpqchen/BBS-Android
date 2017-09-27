package com.twtstudio.bbs.bdpqchen.bbs.commons.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.VersionUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.WindowUtil;

/**
 * Created by bdpqchen on 17-9-26.
 */

public class BottomToolsView extends LinearLayout {

    private Context mContext;
    private LinearLayout mLayout;
    private float mIconSize;
    private int mIconTint;

    public BottomToolsView(Context context) {
        super(context);
        init(null);
    }

    public BottomToolsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mContext = getContext();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomToolsView);
            mIconTint = typedArray.getColor(R.styleable.BottomToolsView_tabIconTint, getResources().getColor(R.color.colorTintIconBlack));
            mIconSize = typedArray.getDimensionPixelSize(R.styleable.BottomToolsView_tabIconSize, 24);
            typedArray.recycle();
        }
        mLayout = new LinearLayout(mContext);
        mLayout.setOrientation(HORIZONTAL);
        mLayout.setGravity(Gravity.CENTER);
        if (VersionUtil.eaLollipop()) {
            mLayout.setElevation(5.5f);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mLayout, params);

    }

    private int getImageMargin(int resSize, int defWidth) {
        int rest = WindowUtil.getWindowWidth(mContext) / resSize - defWidth;
        return rest / 2;
    }

    public void addTabs(@DrawableRes int[] reses, OnClickListener listener) {
        LogUtil.dd("addTabs");
        int defWidth = (int) mIconSize;
        int size = reses.length;
        int imageMargin = getImageMargin(size, defWidth);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(defWidth, defWidth);
        lp.setMargins(imageMargin, 0, imageMargin, 0);
        for (int i = 0; i < size; i++) {
            ImageView view = new ImageView(mContext);
            view.setTag(i);
            view.setLayoutParams(lp);
            view.setColorFilter(mIconTint, PorterDuff.Mode.SRC_IN);
            view.setImageResource(reses[i]);
            view.setOnClickListener(listener);
            mLayout.addView(view);
        }

    }




}
