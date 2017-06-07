package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by bdpqchen on 17-6-5.
 */

public final class TextUtil {

    public static String getBoardName(String s){
        return "[" + s + "]";
    }

    public static Spanned getLinkHtml(String s){
        return Html.fromHtml("<u>" + s + "</u>");
    }

}
