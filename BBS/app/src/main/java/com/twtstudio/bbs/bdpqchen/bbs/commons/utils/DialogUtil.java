package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by bdpqchen on 17-5-11.
 */

public final class DialogUtil {

    public static MaterialDialog showProgressDialog(Context context, String title, String content){
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .show();
    }


}
