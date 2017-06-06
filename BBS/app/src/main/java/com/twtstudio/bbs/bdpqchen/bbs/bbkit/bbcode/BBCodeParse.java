package com.twtstudio.bbs.bdpqchen.bbs.bbkit.bbcode;

import android.util.Log;

import java.util.Map;

/**
 * Created by retrox on 20/05/2017.
 */

public class BBCodeParse {

    /**
     * BBcode转html
     *
     * @param text bbcode
     * @return
     */
    public static String bbcode2Html(String text) {
        String html = text;
        if (html == null || html.length() == 0){
            return "";
        }
        Map<String, String> bbMap = BBCodeMaps.getBBcodeMap();

        for (Map.Entry entry : bbMap.entrySet()) {
            if (entry.getKey().toString().contains("\\[list\\](.+?)\\[/list\\]")) {
                html = bbcodeListParse(html);
            }
            html = html.replaceAll(entry.getKey().toString(), entry.getValue().toString());
        }
//        html = html.replace("[quote]", "<blockquote>")
//                        .replace("[/quote]", "</blockquote>");


        return html;
    }


    /**
     * Parse List Tag: [list] [*]Entry 1 [*]Entry 2 [/list] or [list] *Entry 1 *Entry 2 [/list]
     *
     * @param html html text
     * @return htmla
     */
    private static String bbcodeListParse(String html) {
        String listTagStart = "[list]";
        String listTagEnd = "[/list]";
        String asteriskTag1 = "[*]";
        String asteriskTag2 = "*";

        int pos = 0;
        if (html == null) {
            return "";
        }
        // Only replace * which contains in [list]...[/list]
        while (html.indexOf(listTagStart, pos) != -1) {
            int sPos = html.indexOf(listTagStart, pos);
            int ePos = html.indexOf(listTagEnd, sPos) + listTagEnd.length();
            pos = ePos;

            boolean isAsteriskTag = false;

            String str1 = html.substring(sPos, ePos);
            String str2 = html.substring(sPos, ePos);

            // This must be first step

/*
            if (str1.contains(asteriskTag1)) {
                while (str1.contains(asteriskTag1)) {
                    Log.d("[*]", "invalid");
                    str1 = str1.replace("[*]", "\"[*]\"");
//                    str1 = str1.replaceAll("\\[\\*\\](.+?)\\[", "<li>$1</li>\\[");
                }
                isAsteriskTag = true;
            }
*/
           /* if (html.contains(asteriskTag2)) {
                str1 = str1.replaceAll("\\*", asteriskTag1);
                while (str1.contains(asteriskTag1)) {
                    str1 = str1.replace("[*]", "\"[*]\"");
//                    str1 = str1.replaceAll("\\[\\*\\](.+?)\\[", "<li>$1</li>\\[");
                }
                isAsteriskTag = true;
            }*/
            if (isAsteriskTag) {
                html = html.substring(0, html.indexOf(str2)) + str1 + html.substring(html.indexOf(str2) + str2.length(), html.length());
                Log.d("replaced", html);
            }
            Log.d("return", html);
        }
        return html;
    }
}