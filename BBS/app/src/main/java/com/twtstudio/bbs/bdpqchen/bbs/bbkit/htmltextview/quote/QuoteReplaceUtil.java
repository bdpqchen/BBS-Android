package com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview.quote;

import android.text.Spannable;
import android.text.style.QuoteSpan;

/**
 * Created by retrox on 28/05/2017.
 */

public class QuoteReplaceUtil {
    public static void replaceQuoteSpans(Spannable spannable) {
        QuoteSpan[] quoteSpans = spannable.getSpans(0, spannable.length(), QuoteSpan.class);
        for (QuoteSpan quoteSpan : quoteSpans) {
            int start = spannable.getSpanStart(quoteSpan);
            int end = spannable.getSpanEnd(quoteSpan);
            int flags = spannable.getSpanFlags(quoteSpan);
            spannable.removeSpan(quoteSpan);
            spannable.setSpan(new CustomQuoteSpan(
//                            ResourceUtil.getColor()
//                            Color.parseColor(R.color.colorPrimary + ""),
//                            Color.parseColor(R.color.colorTvBlackMain + ""),
                            8,
                            16),
                    start,
                    end,
                    flags);
        }
    }
}
