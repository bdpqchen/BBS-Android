/*
 * Copyright (C) 2015 Heliangwei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview;

import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

/**
 * Copied from http://stackoverflow.com/questions/8558732
 */
public class LocalLinkMovementMethod extends LinkMovementMethod {
    static LocalLinkMovementMethod sInstance;
    private static Handler mHandler = null;
    public static LocalLinkMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new LocalLinkMovementMethod();

        return sInstance;
    }

    public static LocalLinkMovementMethod getInstance(Handler handler) {
        mHandler = handler;
        return getInstance();
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            for (ClickableSpan spanned : link) {
                Log.d("span", spanned.toString());
            }


            Object[] spans = buffer.getSpans(off, off, ImageSpan.class);
            if (spans.length != 0) {
/*
                if (action == MotionEvent.ACTION_UP) {
                    spans[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
*/
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(spans[0]),
                            buffer.getSpanEnd(spans[0]));
                    MessageSpan obj = new MessageSpan();
                    obj.setObj(spans);
                    obj.setView(widget);
                    Message message = mHandler.obtainMessage();
                    message.obj = obj;
                    message.what = 200;
                    message.sendToTarget();

//                }
                /*
                if (widget instanceof HtmlTextView) {
                    ((HtmlTextView) widget).linkHit = true;
                }
*/
                return true;
            }
            /*if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));

                }

                if (widget instanceof HtmlTextView) {
                    ((HtmlTextView) widget).linkHit = true;
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
                Touch.onTouchEvent(widget, buffer, event);
                return false;
            }*/
        }
        return Touch.onTouchEvent(widget, buffer, event);
    }
}
