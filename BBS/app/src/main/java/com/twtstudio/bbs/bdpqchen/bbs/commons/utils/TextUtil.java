package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.EditText;

import com.github.rjeschke.txtmark.Processor;
import com.twtstudio.bbs.bdpqchen.bbs.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient.BASE_URL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.ANONYMOUS_NAME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.BASE_HOST;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.BASE_HOST_TWT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.FORUM;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_QUOTE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PRE_MD_IMG;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_AT_USER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_COMMENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.TAG_MSG_REPLY;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.THREAD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USER;

/**
 * Created by bdpqchen on 17-6-5.
 */

public final class TextUtil {

    public static String getBoardName(String s) {
        //cj 说看着这个方括号非常难受
//        return "[" + s + "]";
        return s;
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
        if (name == null || nickname == null) return Html.fromHtml("");
        if (name.equals(ANONYMOUS_NAME)) return Html.fromHtml(name);
        String friend = "<font color=\'#e77574\'> [好友] </font>";
        String result = getTwoNames(name, nickname);
        if (IsUtil.INSTANCE.is1(isFriend)) result = friend + result;
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
//            LogUtil.dd("result is", content);
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
        return new StringBuilder("回复量 : ")
                .append(postCount)
                .append("    ")
                .append("时间 : ")
                .append(StampUtil.getDatetimeByStamp(datetime))
                .toString();
    }

    private static String getFloorAndAnon(int floor, int anonymous) {
        return isAnon(anonymous).concat("回复于#").concat(String.valueOf(floor));
    }

    public static String getPostBottomInfo(int postCount, int datetime, int floor, int anonymous) {
        return getFloorAndAnon(floor, anonymous).concat("    ").concat(getPostCountAndTime(postCount, datetime));
    }

    private static String isAnon(int status) {
        return IsUtil.INSTANCE.is1(status) ? "匿名" : "";
    }

    //去掉最后面的两层的引用
    public static String cutTwoQuote(String str0) {
        StringBuilder strNew = new StringBuilder();
        String key = "> > ";
        if (str0.contains(key)) {
            //去掉末尾的\\n
            int i = str0.indexOf(key);
            str0 = str0.substring(0, i);
            String strStart = str0.substring(0, i - 3);
            String strEnd = str0.substring(str0.length() - 3, str0.length());
            strEnd = strEnd.replace("\n", "");
            str0 = strStart + strEnd;
        }
        strNew.append(str0);
        return strNew.toString();
    }

    //添加两层的引用并截断1层 和 2层太长的部分
    public static String addTwoQuote(String str0) {
        String key = "> ";
        if (str0.contains(key)) {
            str0 = str0.replaceAll("\n> \n>", "\n> ");
            int p = str0.indexOf(key);
            String start = str0.substring(0, p);
            start = cutIfTooLong(start);
            start = start.replaceAll("\n", "\n> ");
            String end = str0.substring(p + 2, str0.length());
            end = cutIfTooLong(end);
            end = end.replaceAll("> ", "> > ");
            str0 = start + "\n> >" + end;
        } else {
            str0 = cutIfTooLong(str0);
            str0 = str0.replaceAll("\n", "\n> ");
        }
        return str0;
    }

    private static String cutIfTooLong(String s) {
        if (s.length() > MAX_LENGTH_QUOTE) {
            return s.substring(0, MAX_LENGTH_QUOTE) + "...";
        }
        return s;
    }

    public static String getCommentContent(int floor, String authorName) {
        return "\n> 回复 #" + floor + " " + authorName + " :\n> \n> ";
    }

    private static boolean isMatch(String pattern, String matcher) {
        return match(pattern, matcher).find();
    }

    private static Matcher match(String pattern, String matcher) {
        Pattern p = Pattern.compile(pattern);
        return p.matcher(matcher);
    }

    public static boolean isThread(String url) {
        return isMatch("/" + FORUM + "/" + THREAD + "/\\d+", url);
    }

    public static boolean isImg(String url) {
        return isMatch("/api/img/\\d+", url);
    }

    public static boolean isInnerLink(Uri uri) {
        if (uri == null || uri.getHost() == null) return false;
        return uri.getHost().equals(BASE_HOST) || uri.getHost().equals(BASE_HOST_TWT);
    }

    private static String getAtUserStartPattern() {
        return "/" + USER + "/";
    }

    private static String getAtUserPattern() {
        return getAtUserStartPattern() + "\\d+";
    }

    public static boolean isAtUserLink(Uri uri) {
        return uri != null && isMatch(getAtUserPattern(), uri.toString());
    }

    public static int getAtUid(String str) {
        Matcher matcherStart = match(getAtUserStartPattern(), str);
        Matcher matcherEnd = match(getAtUserPattern(), str);
        if (matcherStart.find() && matcherEnd.find()) {
            return CastUtil.parse2int(str.substring(matcherStart.end(), matcherEnd.end()));
        }
        return 0;
    }

    public static boolean isOuterLink(Uri uri) {
        String link = uri.toString();
        return link.length() > 2 && uri.getHost() != null;
    }

    public static void addImg2Content(int imgId, EditText editText) {
        String addStr = " " + PRE_MD_IMG + (imgId) + ")" + " ";
        insertStr(addStr, editText);
    }

    public static void addAt2Content(String name, EditText editText) {
        insertStr("(@" + name + ")", editText);
    }

    private static void insertStr(String addStr, EditText editText) {
        int index = editText.getSelectionStart();
        Editable editable = editText.getEditableText();
        if (index < 0 || index == editText.length()) {
            editable.append(addStr);
        } else {
            editable.insert(index, addStr);
        }
    }

    public static String getAtContent(String content, HashMap<String, Integer> map) {
        StringBuilder result = new StringBuilder("");
        int lastIndex = 0;
        //the pattern can match so complex string properly. "3 (@bd)a(@2(@na)(@df(@dfd)"
        Matcher matcher = match("\\(@[^()]{2,12}\\)", content);
        while (matcher.find()) {
            //find a username to match it's uid. example ( @bdpqchen ) -> [@bdpqchen](/user/17004)so, start -2, end -1
            String name = content.substring(matcher.start() + 2, matcher.end() - 1);
            if (map.containsKey(name)) {
                result.append(content.substring(lastIndex, matcher.start()))
                        .append("[@").append(name).append("](/")
                        .append(USER).append("/").append(map.get(name)).append(")");
                lastIndex = matcher.end();
                LogUtil.dd("index is " + lastIndex, result.toString());
            }
        }
        if (lastIndex == 0) {
            result = new StringBuilder(content);
        }
//        LogUtil.dd("---.>", result.toString());
        return result.toString();
    }

    public static String getConvertedJson(@NotNull String json) {
        String result = json;
        Matcher matcher = match("\\{\"err\":\\d+,\"data\":\"", json);
        if (matcher.find()) {
//            LogUtil.dd("matched");
            result = json.substring(0, matcher.end() - 1)
                    .concat("{},\"message\":")
                    .concat(json.substring(matcher.end() - 1, json.length()));
        }
        return result;
    }

    public static String getMsgActionText(int tag) {
        switch (tag) {
            case TAG_MSG_AT_USER:
                return "提到";
            case TAG_MSG_COMMENT:
                return "回复";
            case TAG_MSG_REPLY:
                return "评论";
            default:
                return "";
        }
    }
}
