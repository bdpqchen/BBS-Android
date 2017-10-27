package com.twtstudio.bbs.bdpqchen.bbs.commons.view.reminder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bdpqchen on 17-10-7.
 */

public class Reminder {

    private static final String TAG = "Reminder";

    private Activity mActivity;
    private ReminderView mReminderView;

    public Reminder() {
    }

    public Reminder(Activity activity) {
        this.mActivity = activity;
    }

    public Reminder(Activity activity, Params params) {
        this.mActivity = activity;
        mReminderView = new ReminderView(mActivity);
        mReminderView.setParams(params);
    }

    public void show() {
        if (Utils.notNull(mReminderView)) {
            final ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
//            final ViewGroup contentLayout = decorView.findViewById(android.R.id.content);
            if (!Utils.notNull(mReminderView.getParent())) {
                decorView.addView(mReminderView);
            }
        }
    }

    public static class Builder {
        private Params mParams = new Params();
        private Activity mActivity;

        public Builder(Activity activity) {
            this.mActivity = activity;
        }

        public Builder setMessage(String msg) {
            mParams.message = msg;
            return this;
        }

        public Builder setMessageTextSize(int textSize) {
            mParams.messageTextSize = textSize;
            return this;
        }

        public Builder setMessageTextColor(int color) {
            mParams.messageTextColor = color;
            return this;
        }

        public Builder setAction(String name, OnActionClickListener listener) {
            setAction(name, "", listener);
            return this;
        }
        public Builder setAction(String name, String clickedName, OnActionClickListener listener) {
            mParams.actionName = name;
            mParams.actionListener = listener;
            mParams.clickedActionName = clickedName;
            return this;
        }

        public Builder setActionTextSize(int textSize) {
            mParams.actionTextSize = textSize;
            return this;
        }

        public Builder setActionTextColor(int color) {
            mParams.actionTextColor = color;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor){
            mParams.backgroundColor = backgroundColor;
            return this;
        }
        public Builder setDuration(int duration){
            mParams.duration = duration;
            return this;
        }

        private Reminder create() {
            return new Reminder(mActivity, mParams);
        }

        public void show() {
            create().show();
        }

    }

    final static class Params {
        String message;
        String actionName;
        String clickedActionName;
        OnActionClickListener actionListener;
        int messageTextSize;
        int actionTextSize;
        int backgroundColor;
        int messageTextColor;
        int actionTextColor;
        int duration = 2000;
    }

    public interface OnActionClickListener extends View.OnClickListener {
        @Override
        void onClick(View view);
    }

}
