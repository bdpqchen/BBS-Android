package com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview;

import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.bbkit.photo.BigPhotoActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by retrox on 16/08/2017.
 * 用于处理图片点击 链接点击 和 自由复制
 * {@link LocalLinkMovementMethod } <-- 这个货是处理图片 链接点击的（不过现在不用了）
 * {@link ArrowKeyMovementMethod} <-- 从 {@link TextView }setTextIsSelectable方法里面看到的 然后继承 拓展功能
 */

public class RichTextMovementMethod extends ArrowKeyMovementMethod {

    private static RichTextMovementMethod sInstance;

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new RichTextMovementMethod();
        }

        return sInstance;
    }

    private boolean isMoved = false; //设置滑动标志位来模拟点击操作（学习自view源码）: DOWN -> 没有MOVE -> UP == 点击

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
            ImageSpan[] imageSpans = buffer.getSpans(off, off, ImageSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP && !isMoved) {
                    ClickableSpan span = link[0];
                    if (span instanceof URLSpan) {
                        // todo 优化代码质量 封装一下什么的
                        // 用于从URL中取出ThreadID跳转 那一堆其他的东西if什么的是用来判断ThreadURL的
                        // 来避免跳转其他URL 把其他URL的跳转权利下放回系统
                        String url = ((URLSpan) span).getURL();
                        Uri uri = Uri.parse(url);
                        System.out.println(uri.getHost());
                        if (uri.getHost().equals("bbs.tju.edu.cn")){
                            String[] elements = uri.getEncodedPath().split("/");
                            HashSet<String> elementSet = new HashSet<>();
                            elementSet.addAll(Arrays.asList(elements));
                            if (elementSet.contains("forum")&&elementSet.contains("thread")){
                                String threadId = uri.getLastPathSegment();
                                Intent intent = IntentUtil.toThread(widget.getContext(), Integer.parseInt(threadId)," ",0);
                                widget.getContext().startActivity(intent);
                            }else {
                                link[0].onClick(widget);
                            }
                        }else {
                            link[0].onClick(widget);
                        }
                    }
                }
//                else if (action == MotionEvent.ACTION_DOWN) {
//                    Selection.setSelection(buffer,
//                            buffer.getSpanStart(link[0]),
//                            buffer.getSpanEnd(link[0]));
//                }
                return true;
            } else if (imageSpans.length != 0) {
                //todo 弄得优雅一点 考虑隐式跳
                if (action == MotionEvent.ACTION_UP && !isMoved) {
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
            }
            return super.onTouchEvent(widget, buffer, event);
        }
        return super.onTouchEvent(widget, buffer, event);
    }
}