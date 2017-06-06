package com.twtstudio.bbs.bdpqchen.bbs.bbkit.bbcode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一个naive的HTML处理类
 * Created by retrox on 22/05/2017.
 */

public class NaiveHtmlUtils {

    /**
     * 获取HTML文件里面的IMG标签的SRC地址
     *
     * @param htmlText 带html格式的文本
     */
    public static List<String> GetHtmlImageSrcList(String htmlText) {
        List<String> imgSrc = new ArrayList<String>();
        Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(htmlText);
        while (m.find()) {
            imgSrc.add(m.group(1));
        }
        return imgSrc;
    }


    /**
     * 去掉所有的HTML,获取其中的文本信息
     *
     * @param htmlText
     * @return
     */
    public static String GetHtmlText(String htmlText) {
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlText);
        htmlText = m_html.replaceAll(""); // 过滤HTML标签
        return htmlText;
    }
}
