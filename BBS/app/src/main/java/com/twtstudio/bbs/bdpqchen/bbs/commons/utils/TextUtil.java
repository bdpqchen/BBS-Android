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

    public static String getModifyTime(int time){
        return "最后修改于 " + StampUtil.getDatetimeByStamp(time);
    }

    public static String getTwoNames(String name, String nickname){
        return name + "(" + nickname + ")";
    }

}
