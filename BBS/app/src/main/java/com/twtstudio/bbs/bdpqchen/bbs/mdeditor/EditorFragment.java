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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;

import butterknife.BindView;

public class EditorFragment extends BaseFragment {
    @BindView(R.id.tv_editor_title)
    TextView mTitle;
    @BindView(R.id.et_editor_content)
    EditText mContent;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;

    private PerformEditable mPerformEditable;
    public EditorFragment() {
    }

    public static EditorFragment getInstance() {
        return new EditorFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_editor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LogUtil.dd("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initFragment() {
//        LogUtil.dd("InitView");
        mPerformEditable = new PerformEditable(mContent);
        PerformInputAfter.start(mContent);
        mTitle.setText(getTitle());

    }

    public PerformEditable getPerformEditable() {
        return mPerformEditable;
    }

    //菜单
    private MenuItem mActionSave;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor_frag, menu);
        mActionSave = menu.findItem(R.id.action_done);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public String getContent(){
        if (mContent == null){
            return "";
        }
        return mContent.getText().toString();
    }
    public String getTitle(){
        return mListener.getTitleOfContent();
    }

    OnContentListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnContentListener) context;
    }


}
