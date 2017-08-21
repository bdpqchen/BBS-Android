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

package com.twtstudio.bbs.bdpqchen.bbs.htmltextview;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.picture.BigPhotoActivity;

/**
 * Copied from http://stackoverflow.com/questions/8558732
 */
public class LocalLinkMovementMethod extends LinkMovementMethod {
    static LocalLinkMovementMethod sInstance;
    private static Handler mHandler = null;
    private boolean isMoved = false; //设置滑动标志位来模拟点击操作（学习自view源码）: DOWN -> 没有MOVE -> UP == 点击

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

        if (event.getAction() == MotionEvent.ACTION_UP){
//            Log.d("move --> ","action UP");
            isMoved = false;
        }else if (event.getAction() == MotionEvent.ACTION_DOWN){
//            Log.d("move --> ","action DOWN");
            isMoved = false;
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
//            Log.d("move --> ","action MOVE");
            isMoved = true;
        }

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
            ImageSpan[] imageSpans = buffer.getSpans(off, off, ImageSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP && !isMoved) {
                    ClickableSpan span = link[0];
                    if (span instanceof URLSpan){
                        String url = ((URLSpan) span).getURL();
//                        link[0].onClick(widget);
                        Uri uri = Uri.parse(url);
                        System.out.println(uri.getHost());
                    }
                }
//                else if (action == MotionEvent.ACTION_DOWN) {
//                    Selection.setSelection(buffer,
//                            buffer.getSpanStart(link[0]),
//                            buffer.getSpanEnd(link[0]));
//                }
                return true;
            } else if (imageSpans.length != 0) {
                //todo 弄得优雅一点 考虑隐式转换
                if (action == MotionEvent.ACTION_UP && !isMoved){
                    Intent intent = new Intent(widget.getContext(), BigPhotoActivity.class);
                    intent.putExtra("url", imageSpans[0].getSource());
                    widget.getContext().startActivity(intent);
                }
//                貌似没啥卵用的模仿代码
//                else if (action == MotionEvent.ACTION_DOWN) {
//                    Selection.setSelection(buffer,
//                            buffer.getSpanStart(imageSpans[0]),
//                            buffer.getSpanEnd(imageSpans[0]));
//                }

                return true;
            } else {
                Selection.removeSelection(buffer);
                Touch.onTouchEvent(widget, buffer, event);
                return false;
            }
//  貌似没啥卵用的老代码
//            for (ImageSpan imageSpan : imageSpans) {
//                Log.d("span", "onTouchEvent: img --> " + imageSpan.getSource());
//                Intent intent = new Intent(widget.getContext(), BigPhotoActivity.class);
//                intent.putExtra("url",imageSpan.getSource());
//                widget.getContext().startActivity(intent);
//            }
//            for (ClickableSpan spanned : link) {
//                Log.d("span", spanned.toString());
//            }
//
//            if (link.length != 0){
//                link[0].onClick(widget);
//            }
//
//            if (imageSpans.length != 0) {
//                    Selection.setSelection(buffer,
//                            buffer.getSpanStart(imageSpans[0]),
//                            buffer.getSpanEnd(imageSpans[0]));
//                    MessageSpan obj = new MessageSpan();
//                    obj.setObj(imageSpans);
//                    obj.setView(widget);
//                    Message message = mHandler.obtainMessage();
//                    message.obj = obj;
//                    message.what = 200;
//                    message.sendToTarget();
//
//                return true;
//            }
        }
        return Touch.onTouchEvent(widget, buffer, event);
    }
}
