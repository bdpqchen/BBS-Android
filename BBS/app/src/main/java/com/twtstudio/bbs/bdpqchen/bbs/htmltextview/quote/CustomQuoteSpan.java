package com.twtstudio.bbs.bdpqchen.bbs.htmltextview.quote;

/**
 * Created by retrox on 28/05/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

/**
 * android.text.style.QuoteSpan hard-codes the strip color and gap. :(
 */
public class CustomQuoteSpan implements LeadingMarginSpan, LineBackgroundSpan {
    private int backgroundColor;
    private int stripeColor;
    private final float stripeWidth;
    private final float gap;

    public CustomQuoteSpan(float stripeWidth, float gap) {
        if (PrefUtil.isNightMode()){
            //夜间模式
            this.backgroundColor = Color.parseColor("#303b41");
            this.stripeColor = Color.parseColor("#f4f0f0");
        }else{
            this.stripeColor = Color.parseColor("#44f49979");
            this.backgroundColor = Color.parseColor("#44EEEEEE");
        }

        this.stripeWidth = stripeWidth;
        this.gap = gap;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return (int) (stripeWidth + gap);
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom,
                                  CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int paintColor = p.getColor();
        p.setStyle(Paint.Style.FILL);
        p.setColor(stripeColor);
        c.drawRect(x, top, x + dir * stripeWidth, bottom, p);
        p.setStyle(style);
        p.setColor(paintColor);
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int paintColor = p.getColor();
        p.setColor(backgroundColor);
        c.drawRect(left, top, right, bottom, p);
        p.setColor(paintColor);
    }
}