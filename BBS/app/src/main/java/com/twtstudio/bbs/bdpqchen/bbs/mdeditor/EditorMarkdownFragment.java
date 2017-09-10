/*
 * Copyright 2016. SHENQINCI(沈钦赐)<dev@qinc.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.GlideImageGeter;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.HtmlTextView;

import butterknife.BindView;

/**
 * 预览界面
 * Created by 沈钦赐 on 16/1/21.
 */
public class EditorMarkdownFragment extends BaseFragment {
    protected TextView mName;
    @BindView(R.id.tv_preview_title)
    TextView mTitle;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    @BindView(R.id.htv_preview_content)
    HtmlTextView mHtvPreviewContent;
    private String mContent;

    public interface OnContenteListener {
        public void getContent();
    }

    public EditorMarkdownFragment() {
    }

    public static EditorMarkdownFragment getInstance() {
        return new EditorMarkdownFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_markdown;
    }

    @Override
    protected void initFragment() {
        mTitle.setText(mListener.getTitleOfContent());
    }

    OnContentListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnContentListener) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            drawPreview();
        }
    }

    private void drawPreview() {
        mContent = mListener.getContent();
        String htmlContent = TextUtil.convert2HtmlContent(mContent);
        mHtvPreviewContent.setHtml(htmlContent, new GlideImageGeter(mContext, mHtvPreviewContent));

    }




}
