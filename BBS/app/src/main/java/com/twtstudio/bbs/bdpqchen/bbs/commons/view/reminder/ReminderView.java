package com.twtstudio.bbs.bdpqchen.bbs.commons.view.reminder;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;


/**
 * Created by bdpqchen on 17-10-7.
 */

final class ReminderView extends LinearLayout {

    private LinearLayout mLayout;
    private TextView mTvMessage, mTvAction;
    private Context mContext;
    private Animation mLayoutOutAnim;
    private static int mDuration = 2000;

    public ReminderView(Context context) {
        super(context);
        initViews(context);
    }

    public ReminderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ReminderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ReminderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context) {
        mContext = context;
        inflate(mContext, R.layout.layout_reminder, this);
        mLayout = (LinearLayout) findViewById(R.id.ll_reminder);
        mLayout.setMinimumHeight(Utils.getMinHeight(mContext));
        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissLayout();
            }
        });
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        mTvAction = (TextView) findViewById(R.id.tv_action);
    }

    public void setParams(final Reminder.Params params) {
        if (params == null) return;
        //Set message view.
        if (Utils.notNull(params.message)) {
            mTvMessage.setText(params.message);
        }
        if (Utils.notNull(params.messageTextColor)) {
            mTvMessage.setTextColor(getColor(params.messageTextColor));
        }
        if (Utils.notNull(params.messageTextSize)) {
            mTvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, params.messageTextSize);
        }
        if (Utils.notNull(params.actionListener)) {
            visible(mTvAction);
            if (Utils.notNull(params.actionName)) {
                mTvAction.setText(params.actionName);
            }
            if (Utils.notNull(params.actionTextColor)) {
                mTvAction.setTextColor(getColor(params.actionTextColor));
            }
            if (Utils.notNull(params.actionTextSize)) {
                mTvAction.setTextSize(TypedValue.COMPLEX_UNIT_SP, params.actionTextSize);
            }
            mTvAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.notNull(params.clickedActionName)) {
                        mTvAction.setText(params.clickedActionName);
                    }
                    params.actionListener.onClick(view);
                }
            });
        }
        if (Utils.notNull(params.backgroundColor)) {
            mLayout.setBackgroundColor(getColor(params.backgroundColor));
        }
        if (Utils.notNull(params.duration)) {
            mDuration = params.duration;
        }

        setLayoutInAnim();
        setLayoutOutAnim();
    }

    private void setLayoutInAnim() {
        Animation layoutInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.layout_in);
        layoutInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissLayout();
                    }
                }, mDuration);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        setAnimation(layoutInAnim);
    }

    private void setLayoutOutAnim() {
        mLayoutOutAnim = AnimationUtils.loadAnimation(mContext, R.anim.layout_out);
    }

    private void dismissLayout() {
        mLayoutOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                destroyLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(mLayoutOutAnim);
    }

    private void destroyLayout() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewParent parent = getParent();
                if (parent != null) {
                    ReminderView.this.clearAnimation();
                    ((ViewGroup) parent).removeView(ReminderView.this);
                }
            }
        }, 100);
    }

    private int getColor(int color) {
        return Utils.getColor(mContext, color);
    }

    private void visible(View view) {
        if (Utils.notNull(view)) {
            view.setVisibility(VISIBLE);
        }
    }

    private void gone(View view) {
        if (Utils.notNull(view)) {
            view.setVisibility(GONE);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, 0, r, mLayout.getMeasuredHeight());
    }

}
