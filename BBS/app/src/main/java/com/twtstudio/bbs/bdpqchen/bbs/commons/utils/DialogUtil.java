package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by bdpqchen on 17-5-11.
 */

public final class DialogUtil {

    public static MaterialDialog showProgressDialog(Context context, String title, String content){
        return new MaterialDialog.Builder(context)
                .title("提示")
                .content("正在上传，请稍后..")
                .progress(true, 0)
                .show();
    }

}
