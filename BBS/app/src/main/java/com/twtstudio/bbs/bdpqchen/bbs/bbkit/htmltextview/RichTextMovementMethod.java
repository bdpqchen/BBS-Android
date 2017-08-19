package com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview;

import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.picture.BigPhotoActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.CastUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;

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

            if (isLink(link)) {
                if (isActionUp(action)) {
                    ClickableSpan span = link[0];
                    if (span instanceof URLSpan) {
                        Uri uri = Uri.parse(((URLSpan) span).getURL());
//                        LogUtil.dd("uri", uri.toString());
                        if (isInteriorThreadLink(uri)) {
                            int threadId = CastUtil.parse2intWithMin(uri.getLastPathSegment());
                            Intent intent = IntentUtil.toThread(widget.getContext(), threadId);
                            widget.getContext().startActivity(intent);
                        } else {
                            // 调用系统默认链接点击事件
                            link[0].onClick(widget);
                        }
                    }
                }
                /*else if (action == MotionEvent.ACTION_DOWN) {
                    LogUtil.dd("action", "Down");
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }*/
                return true;
            } else if (isImage(imageSpans)) {
                if (isActionUp(action)) {
                    Intent intent = new Intent(widget.getContext(), BigPhotoActivity.class);
                    intent.putExtra("url", imageSpans[0].getSource());
                    widget.getContext().startActivity(intent);
                }
                return true;
            }
            return super.onTouchEvent(widget, buffer, event);
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    private boolean isThread(String url) {
        return url.contains("/forum/thread/");
    }

    private boolean isLink(ClickableSpan[] link) {
        return link.length != 0;
    }

    private boolean isImage(ImageSpan[] imageSpans) {
        return imageSpans.length != 0;
    }

    private boolean isActionUp(int action) {
        return action == MotionEvent.ACTION_UP && !isMoved;
    }

    private boolean isInteriorLink(Uri uri) {
        return uri.getHost().equals(RxDoHttpClient.BASE_HOST);
    }

    private boolean isInteriorThreadLink(Uri uri) {
        return isInteriorLink(uri) && isThread(uri.toString());
    }
}