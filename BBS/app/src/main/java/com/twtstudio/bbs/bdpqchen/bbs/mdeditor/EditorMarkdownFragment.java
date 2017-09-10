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
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import butterknife.BindView;

/**
 * 预览界面
 * Created by 沈钦赐 on 16/1/21.
 */
public class EditorMarkdownFragment extends BaseFragment {
    protected TextView mName;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.markdownView)
    MarkdownPreviewView mMarkdownView;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    private String mContent;

    public interface OnContenteListener {
        public void getContent();
    }

    public EditorMarkdownFragment() {
    }

    public static EditorMarkdownFragment getInstance() {
        EditorMarkdownFragment editorFragment = new EditorMarkdownFragment();
        return editorFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_markdown;
    }

    @Override
    protected void initFragment() {
//        mMarkdownView.parseMarkdown();
        getContent();
//        HandlerUtil.postDelay(this::getContent, 3000);
    }

    private void getContent(){
        String content = mListener.getContent();
        LogUtil.dd("inner fragment content", content + "---");
    }

    @Override
    public boolean hasMenu() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor_preview_frag, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    OnContentListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnContentListener) context;
    }
}
