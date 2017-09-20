package com.twtstudio.bbs.bdpqchen.bbs.htmltextview.quote;

import android.text.Spannable;
import android.text.style.QuoteSpan;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

/**
 * Created by retrox on 28/05/2017.
 */

public class QuoteReplaceUtil {
    public static void replaceQuoteSpans(Spannable spannable) {
        QuoteSpan[] quoteSpans = spannable.getSpans(0, spannable.length(), QuoteSpan.class);
        for (QuoteSpan quoteSpan : quoteSpans) {
            int start = spannable.getSpanStart(quoteSpan);
            int end = spannable.getSpanEnd(quoteSpan);
            LogUtil.dd("start", String.valueOf(start));
            LogUtil.dd("end", String.valueOf(end));

            int flags = spannable.getSpanFlags(quoteSpan);
            spannable.removeSpan(quoteSpan);
            spannable.setSpan(
                    new CustomQuoteSpan(8, 20),
                    start,
                    end,
                    flags);
        }
    }
}
