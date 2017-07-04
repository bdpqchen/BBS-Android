package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.github.rjeschke.txtmark.Processor;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient.BASE_URL;

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
        if (nickname != null && nickname.length() > 12){
            nickname = nickname.substring(0, 11);
        }
        return name + "(" + nickname + ")";
    }

    public static String getReplacedContent(String content){
        content = content.replaceAll("attach:", BASE_URL + "img/");
        content = content.replaceAll("<img", "<br /><img");
        return content;
    }

    public static String formatContent(String contentBefore){
        String content = "";
        if (contentBefore != null && contentBefore.length() > 0){
            content = Processor.process(contentBefore);
            content = TextUtil.getReplacedContent(content);
            content = content.replaceAll("attach:", BASE_URL + "img/");
            content = content.replaceAll("<img", "<br /><img");
        }
        return content;
    }

    public static SpannableString getPastDays(Context context, int create) {
        String days = StampUtil.getDaysFromCreateToNow(create) + "天";
        int daysLength = days.length();
        SpannableString styledText = new SpannableString(days);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.tvTextSizeNormal), 0, daysLength - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.tvTextSizeVeryLittle), daysLength - 1, daysLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //TextView.BufferType.SPANNABLE
        return styledText;
    }

}
