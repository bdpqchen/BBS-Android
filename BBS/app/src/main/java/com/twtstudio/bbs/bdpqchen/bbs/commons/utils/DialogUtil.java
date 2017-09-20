package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by bdpqchen on 17-5-11.
 */

public final class DialogUtil {

    public static MaterialDialog showProgressDialog(Context context, String content) {
        return getBuilder(context, content)
                .progress(true, 0)
                .show();
    }

    public static MaterialDialog showProgressDialog(Context context, String title, String content) {
        return getBuilder(context, title, content)
                .progress(true, 0)
                .show();
    }

    private static MaterialDialog.Builder getBuilder(Context context, String content) {
        return new MaterialDialog.Builder(context).content(content);
    }

    private static MaterialDialog.Builder getBuilder(Context context, String title, String content) {
        return new MaterialDialog.Builder(context).title(title).content(content);
    }

    public static MaterialDialog alertDialog(
            Context context,
            String content,
            String positiveText,
            String negativeText,
            MaterialDialog.SingleButtonCallback positiveCallback,
            MaterialDialog.SingleButtonCallback negativeCallback) {
        return getBuilder(context, content)
                .positiveText(positiveText)
                .onPositive(positiveCallback)
                .negativeText(negativeText)
                .onNegative(negativeCallback)
                .show();
    }

    public static MaterialDialog noticeDialog(Context con, String content) {
        return getBuilder(con, content).show();
    }

    public static MaterialDialog noticeDialog(Context con, String title, String content) {
        return getBuilder(con, title, content).show();
    }

    public static MaterialDialog inputDialog(Context context, String content, MaterialDialog.InputCallback inputCallback) {
        return new MaterialDialog.Builder(context)
                .input(content, "", false, inputCallback)
                .title("跳楼层")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .positiveText("开始")
                .show();
    }

    public static MaterialDialog multiLineInputDialog(Context context, String title, String hint, MaterialDialog.InputCallback callback) {
        return new MaterialDialog.Builder(context)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                .input(hint, "", false, callback)
                .title(title)
                .negativeText("取消")
                .show();

    }
}
