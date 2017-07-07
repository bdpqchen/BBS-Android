package com.twtstudio.bbs.bdpqchen.bbs.bbkit.htmltextview;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import org.xml.sax.XMLReader;

import java.util.Locale;

public class MyTagHandler implements Html.TagHandler {
  
    private Context mContext;
      
    public MyTagHandler(Context context) {
        mContext = context.getApplicationContext();  
    }  
      
    @Override  
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
  
        // 处理标签<img>  
        if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
            // 获取长度  
            int len = output.length();  
            // 获取图片地址  
            ImageSpan[] images = output.getSpans(len-1, len, ImageSpan.class);
            String imgURL = images[0].getSource();  
              
            // 使图片可点击并监听点击事件  
            output.setSpan(new ClickableImage(mContext, imgURL), len-1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }  
    }  
      
    private class ClickableImage extends ClickableSpan {
  
        private String url;  
        private Context context;  
          
        public ClickableImage(Context context, String url) {  
            this.context = context;  
            this.url = url;  
        }  
          
        @Override  
        public void onClick(View widget) {
            // 进行图片点击之后的处理

            LogUtil.dd("you have click image", "1111");
        }  
    }  
}  