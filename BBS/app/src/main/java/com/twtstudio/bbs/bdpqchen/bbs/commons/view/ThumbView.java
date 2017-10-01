package com.twtstudio.bbs.bdpqchen.bbs.commons.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;

/**
 * Created by bdpqchen on 17-9-29.
 */

public class ThumbView extends LinearLayout {

    private int mLike;
    private boolean mIsLiked = false;
    private Context mContext;
    private LinearLayout mLayout;
    private ImageView mImageView;
    private TextView mTextView;
    private float mScale;

    public ThumbView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mContext = getContext();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ThumbView);
            String count = typedArray.getString(R.styleable.ThumbView_like);
            if (count != null && count.length() > 0) {
                mLike = Integer.valueOf(count);
            }
            mIsLiked = typedArray.getBoolean(R.styleable.ThumbView_is_liked, false);
            mScale = typedArray.getFloat(R.styleable.ThumbView_scale, 1);
            typedArray.recycle();
        }
        mLayout = new LinearLayout(mContext);
        mLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mLayout, params);
        mTextView = new TextView(mContext);
        mImageView = new ImageView(mContext);
        addThumb();
        addCount();
        updateStatus();
    }

    private void updateStatus() {
        int color = getResources().getColor(R.color.colorTintIconBlack);
        if (mIsLiked) {
            color = getResources().getColor(R.color.colorPrimaryCopy);
        }
        if (mTextView != null) {
            mTextView.setTextColor(color);
            mTextView.setText(toStr(mLike));
        }
        if (mImageView != null) mImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    private String toStr(int count) {
        return String.valueOf(Html.fromHtml("<b>" + count + "</b>"));
    }

    private void addCount() {
        setLikeCount(mLike);
    }

    private void addThumb() {
        mImageView.setImageResource(R.drawable.ic_thumb_up_white_24dp);
        mImageView.setScaleType(ImageView.ScaleType.CENTER);
        mImageView.setScaleX(mScale);
        mImageView.setScaleY(mScale);
        mImageView.setOnClickListener(view -> like());
        mTextView.setTextSize(17);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 0, 10, 0);
        mTextView.setLayoutParams(lp);
        mTextView.setClickable(true);
        mTextView.setFocusable(true);

        mLayout.addView(mImageView);
        mLayout.addView(mTextView);
    }

    public void setThumbOnClickListener(OnClickListener listener) {
        setOnClickListener(listener);
    }

    private void like() {
        if (mIsLiked) unlike();
        else addLike();
        callOnClick();
    }

    public void setLikeCount(int count) {
        if (count < 0) count = 0;
        mLike = count;
        updateStatus();
    }

    public void addLike() {
        mLike++;
        mIsLiked = true;
        rotate(true);
        updateStatus();
    }

    public void unlike() {
        minusLike();
        mIsLiked = false;
        rotate(false);
        updateStatus();
    }

    private void minusLike() {
        if (mLike > 0) mLike--;
        else mLike = 0;
    }

    public void setIsLiked(boolean b) {
        mIsLiked = b;
        updateStatus();
    }

    public void rotate(boolean sec) {
        int anim = R.anim.thumb_down;
        if (sec) anim = R.anim.thumb_up;
        Animation rotateAnimation = AnimationUtils.loadAnimation(mContext, anim);
        mImageView.setAnimation(rotateAnimation);
        mImageView.startAnimation(rotateAnimation);
    }


}
