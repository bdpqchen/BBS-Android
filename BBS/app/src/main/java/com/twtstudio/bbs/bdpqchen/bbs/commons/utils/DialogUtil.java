package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by bdpqchen on 17-5-11.
 */

public final class DialogUtil {

    public static MaterialDialog showProgressDialog(Context context, String title, String content) {
        return new MaterialDialog.Builder(context)
//                .title(title)
                .content(content)
                .progress(true, 0)
                .show();
    }

    public static MaterialDialog alertDialog(
            Context context,
            @Nullable String title,
            String content,
            String positiveText,
            @Nullable String negativeText,
            @Nullable MaterialDialog.SingleButtonCallback positiveCallback,
            @Nullable MaterialDialog.SingleButtonCallback negativeCallback) {

        assert positiveCallback != null;
        assert title != null;
        assert negativeText != null;
        assert negativeCallback != null;
        return new MaterialDialog.Builder(context)
//                .title(title)
                .content(content)
                .positiveText(positiveText)
                .onPositive(positiveCallback)
                .negativeText(negativeText)
                .onNegative(negativeCallback)
                .show();
    }

    public static MaterialDialog noticeDialog(Context con, String title, String content){
        return new MaterialDialog.Builder(con)
//                .title(title)
                .content(content)
                .show();
    }

    public static MaterialDialog inputDialog(Context context, String content, MaterialDialog.InputCallback inputCallback) {
        return new MaterialDialog.Builder(context)
                .input(content, "", false, inputCallback)
                .title("跳楼层")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .positiveText("开始")
                .show();
    }
    public static MaterialDialog multiLineInputDialog(Context context, String title, String hint, MaterialDialog.InputCallback callback){
        return new MaterialDialog.Builder(context)
                .inputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                .input(hint, "", false, callback)
                .title(title)
                .negativeText("取消")
                .show();

    }
}
