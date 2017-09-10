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
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import butterknife.BindView;

/**
 * 编辑界面
 * Created by 沈钦赐 on 16/1/21.
 */
public class EditorFragment extends BaseFragment {
    public static final String FILE_PATH_KEY = "FILE_PATH_KEY";
    @BindView(R.id.tv_editor_title)
    TextView mTitle;
    @BindView(R.id.et_editor_content)
    EditText mContent;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;

    private EditorFragmentPresenter mPresenter;

    private PerformEditable mPerformEditable;
//    private PerformEdit mPerformEdit;
//    private PerformEdit mPerformNameEdit;
    public EditorFragment() {
    }

    public static EditorFragment getInstance(String filePath) {
        EditorFragment editorFragment = new EditorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FILE_PATH_KEY, filePath);
        editorFragment.setArguments(bundle);
        LogUtil.dd("getInstance");
        return editorFragment;
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

/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        String fileTemp = arguments.getString(FILE_PATH_KEY);
        SnackBarUtil.error(this.getActivity(), "oncreated");

        LogUtil.dd("onCreate");
        if (fileTemp == null) {
//            Toast.makeText(AppContext.context(), "路径参数有误！", Toast.LENGTH_SHORT).show();
//            return;
        }


//        File file = new File(fileTemp);
        //创建新文章
//        mPresenter = new EditorFragmentPresenter(file);

        //代码格式化或者插入操作
        mPerformEditable = new PerformEditable(mContent);
        PerformInputAfter.start(mContent);

     */
/*   //撤销和恢复初始化
        mPerformEdit = new PerformEdit(mContent) {
            @Override
            protected void onTextChanged(Editable s) {
                //文本改变
                mPresenter.textChange();
            }
        };*//*

       */
/* mPerformNameEdit = new PerformEdit(mName) {
            @Override
            protected void onTextChanged(Editable s) {
                //文本改变
                mPresenter.textChange();
            }
        };*//*

        //文本输入监听(用于自动输入)
*/
/*
        //装置数据
        if (file.isFile())
            mPresenter.loadFile();*//*
    }
*/
/*
    @Override
    public void onFailure(int errorCode, String message, int flag) {
        switch (flag) {
            case CALL_SAVE:
            case CALL_LOAOD_FILE:
                BaseApplication.showSnackbar(mContent, message);
                break;
            default:
                BaseApplication.showSnackbar(mContent, message);
                break;
        }
    }
*/

    public PerformEditable getPerformEditable() {
        return mPerformEditable;
    }

    @Override
    public boolean hasMenu() {
        return true;
    }

    //菜单
    private MenuItem mActionSave;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor_frag, menu);
        mActionSave = menu.findItem(R.id.action_done);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done://保存
//                mPresenter.save(mName.getText().toString().trim(), mContent.getText().toString().trim());
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

/*
    /*@Override
    public void onReadSuccess(@NonNull String name, @NonNull String content) {
//        mPerformNameEdit.setDefaultText(name.substring(0, name.lastIndexOf(".")));
//        mPerformEdit.setDefaultText(content);
        if (content.length() > 0) {
            //切换到预览界面
//            RxEventBus.getInstance().send(new RxEvent(RxEvent.TYPE_SHOW_PREVIEW, mName.getText().toString(), mContent.getText().toString()));
        }
    }*/

    public String getContent(){
        if (mContent == null){
            return "";
        }
        String str = mContent.getText().toString();
        LogUtil.dd("content_result", str);
        return str;
    }
    public String getTitle(){
        return mListener.getTitleOfContent();
    }

    public void noSave() {
        if (mActionSave == null) return;
        mActionSave.setIcon(R.drawable.ic_action_unsave);
    }

    public void saved() {
        if (mActionSave == null) return;
        mActionSave.setIcon(R.drawable.ic_action_save);
    }

    @Override
    public boolean onBackPressed() {
        if (mPresenter.isSave()) {
            return false;
        }
        onNoSave();
        return true;
    }

    OnContentListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnContentListener) context;
    }

    private void onNoSave() {
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        builder.setMessage("当前文件未保存，是否退出?");
        builder.setNegativeButton("不保存", (dialog, which) -> {
            getActivity().finish();

        }).setNeutralButton("取消", (dialog, which) -> {
            dialog.dismiss();

        }).setPositiveButton("保存", (dialog, which) -> {
            mPresenter.saveForExit(mName.getText().toString().trim(), mContent.getText().toString().trim(), true);

        }).show();*/
    }


}
