package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

import com.github.rjeschke.txtmark.Processor;
import com.twtstudio.bbs.bdpqchen.bbs.R;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient.BASE_URL;

/**
 * Created by bdpqchen on 17-6-5.
 */

public final class TextUtil {

    public static String getBoardName(String s) {
        return "[" + s + "]";
    }

    public static Spanned getLinkHtml(String s) {
        return Html.fromHtml("<u>" + s + "</u>");
    }

    public static String getThreadDateTime(int createTime, int modifyTime) {
        String pre = "发布于 ";
        int resultTime = createTime;
        if (modifyTime > 0 && modifyTime > createTime) {
            pre = "最后修改于 ";
            resultTime = modifyTime;
        }
        return pre + StampUtil.getDatetimeByStamp(resultTime);
    }

    public static Spanned getNameWithFriend(String name, String nickname, int isFriend) {
        boolean is = IsUtil.is1(isFriend);
        String friend = "<font color=\'#e77574\'> [好友] </font>";
        String result = getTwoNames(name, nickname);
        if (is) {
            result = friend + result;
        }
        return Html.fromHtml(result);
    }

    public static String getTwoNames(String name, String nickname) {
        if (nickname == null || isEqual(name, nickname)) {
            return name;
        }
        if (nickname.length() > 12) {
            nickname = nickname.substring(0, 11);
        }
        return name + "(" + nickname + ")";
    }

    private static boolean isEqual(String str0, String str1) {
        return str0.equals(str1);
    }

    private static String getReplacedContent(String content) {
        content = content.replaceAll("attach:", BASE_URL + "img/");
        content = content.replaceAll("<img", "<br /><img");
        return content;
    }

    public static String formatContent(String contentBefore) {
        String content = "";
        if (contentBefore != null && contentBefore.length() > 0) {
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

    public static String getReplacedImageContent(String content) {
        String key = "attach:";
        while (content.contains(key)) {
            int index = content.indexOf(key);
            String start = content.substring(0, index - 4);
            String end = "";
            int beginAt = index + key.length();
            int stopAt = content.length();
            for (int i = beginAt; i < stopAt; i++) {
                if (content.charAt(i) < 48 || content.charAt(i) > 57) {
                    if (content.substring(i, i + 1).equals(")")) {
                        i++;
                    }
                    end = content.substring(i, content.length());
                    break;
                }
            }
            content = start + "[图片]" + end;
//            LogUtil.dd("resultis", content);
        }
        return content;
    }

    public static String getHonor(int points) {
        final int lv1 = 100, lv2 = 500, lv3 = 1000, lv4 = 2000, lv5 = 4000, lv6 = 8000, lv7 = 10000;
        String honor = "新手上路";
        if (points < lv1) {
        } else if (points < lv2) {
            honor = "一般站友";
        } else if (points < lv3) {
            honor = "中级站友";
        } else if (points < lv4) {
            honor = "高级站友";
        } else if (points < lv5) {
            honor = "老站友";
        } else if (points < lv6) {
            honor = "长老级";
        } else if (points < lv7) {
            honor = "本站元老";
        } else if (points > lv7) {
            honor = "开国大佬";
        }
        return honor;
    }

    public static String convert2HtmlContent(String content) {
        if (content == null || content.length() == 0) {
            return "";
        }
        content = content.replaceAll("\n> \n>", "\n>");
        content = content.replaceAll("\n> > \n> >", "\n> >");
        content = Processor.process(content);
        content = content.replaceAll("<img", "<br><img");
        content = content.replaceAll("attach:", BASE_URL + "img/");
        content = content.replaceAll("\n<blockquote>", "<blockquote>");
        content = content.replaceAll("</blockquote>\n", "</blockquote>");
        content = content.replaceAll("\n<p>", "<p>");
        content = content.replaceAll("</p>\n", "</p>");
        content = content.replaceAll("\n", "<br>");
//        LogUtil.dd("final content", content);
        return content;
    }

    public static String getEditorToolbarTitle(final int what) {
        String title = "发表";
        switch (what) {
            case 0:
                return title;
            case 1:
                title = "回复";
                break;
        }
        return title;
    }

    public static String getPostCountAndTime(int postCount, int datetime) {
        return "回复量 : " + postCount + "    " + "时间 : " + StampUtil.getDatetimeByStamp(datetime);
    }

    public static String getFloorAndAnon(int floor, int anonymous) {
        return isAnon(anonymous) + "回复于#" + floor;
    }

    public static String getPostBottomInfo(int postCount, int datetime, int floor, int anonymous){
        return getFloorAndAnon(floor, anonymous) + "    " + getPostCountAndTime(postCount, datetime);
    }

    private static String isAnon(int status) {
        return IsUtil.is1(status) ? "匿名" : "";
    }
}
