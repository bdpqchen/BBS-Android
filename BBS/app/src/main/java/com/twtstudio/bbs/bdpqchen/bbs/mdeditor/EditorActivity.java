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

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.mdeditor.support.ExpandableLinearLayout;
import com.twtstudio.bbs.bdpqchen.bbs.mdeditor.support.TabIconView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_CONTENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_TITLE;


public class EditorActivity extends AppCompatActivity implements View.OnClickListener, OnContentListener{
    public static final String SHARED_ELEMENT_NAME = "SHARED_ELEMENT_NAME";
    public static final String SHARED_ELEMENT_COLOR_NAME = "SHARED_ELEMENT_COLOR_NAME";
    private static final String SCHEME_FILE = "file";
    private static final String SCHEME_Folder = "folder";
    @BindView(R.id.id_toolbar)
    Toolbar mIdToolbar;
    @BindView(R.id.id_appbar)
    AppBarLayout mIdAppbar;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.tabIconView)
    TabIconView mTabIconView;
    @BindView(R.id.action_other_operate)
    ExpandableLinearLayout mExpandLayout;

    private EditorFragment mEditorFragment;
    private EditorMarkdownFragment mEditorMarkdownFragment;
    private String mTitle = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getIntent().getStringExtra(INTENT_EDITOR_TITLE);
//        mTitle = "假设的标题, 或者 回复:xxx";
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        getIntentData();
        mEditorFragment = EditorFragment.getInstance();
        mEditorMarkdownFragment = EditorMarkdownFragment.getInstance();

        initViewPager();
        initTab();
        initActionBar(mIdToolbar);

    }

    private void initActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initViewPager() {
        mViewPager.setAdapter(new EditFragmentAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                //刷新渲染数据
                if (position == 1) {
//                    RxEventBus.getInstance().send(new RxEvent(RxEvent.TYPE_REFRESH_NOTIFY));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTab() {
        mTabIconView.addTab(R.drawable.ic_shortcut_format_list_bulleted, R.id.id_shortcut_list_bulleted, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_list_numbers, R.id.id_shortcut_format_numbers, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_insert_link, R.id.id_shortcut_insert_link, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_insert_photo, R.id.id_shortcut_insert_photo, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_console, R.id.id_shortcut_console, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_bold, R.id.id_shortcut_format_bold, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_italic, R.id.id_shortcut_format_italic, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_1, R.id.id_shortcut_format_header_1, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_2, R.id.id_shortcut_format_header_2, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_3, R.id.id_shortcut_format_header_3, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_quote, R.id.id_shortcut_format_quote, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_xml, R.id.id_shortcut_xml, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_minus, R.id.id_shortcut_minus, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_strikethrough, R.id.id_shortcut_format_strikethrough, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_grid, R.id.id_shortcut_grid, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_4, R.id.id_shortcut_format_header_4, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_5, R.id.id_shortcut_format_header_5, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_6, R.id.id_shortcut_format_header_6, this);
    }

    private final int SYSTEM_GALLERY = 1;

    @Override
    public void onClick(View v) {
        if (R.id.id_shortcut_insert_photo == v.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);// Pick an item fromthe
            intent.setType("image/*");// 从所有图片中进行选择
            startActivityForResult(intent, SYSTEM_GALLERY);
            return;
        } else if (R.id.id_shortcut_insert_link == v.getId()) {
            //插入链接
            insertLink();
            return;
        } else if (R.id.id_shortcut_grid == v.getId()) {
            //插入表格
//            insertTable();
            return;
        }
        //点击事件分发
        mEditorFragment.getPerformEditable().onClick(v);
    }

    private class EditFragmentAdapter extends FragmentPagerAdapter {

        public EditFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mEditorFragment;
            }
            return mEditorMarkdownFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public String getContent(){
        return mEditorFragment.getContent();
    }

    @Override
    public String getTitleOfContent() {
        return mTitle;
    }

    private void getIntentData() {
        Intent intent = this.getIntent();
        int flags = intent.getFlags();
        if ((flags & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0) {
            if (intent.getAction() != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
                if (SCHEME_FILE.equals(intent.getScheme())) {
                    //文件
                    String type = getIntent().getType();
                    // mImportingUri=file:///storage/emulated/0/Vlog.xml
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri uri = intent.getData();

                    if (uri != null && SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
                        //这是一个文件
//                        currentFilePath = FileUtils.uri2FilePath(getBaseContext(), uri);
                    }
                }
            }
        }
    }

    private MenuItem mActionOtherOperate;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        LogUtil.dd("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_editor_act, menu);
        mActionOtherOperate = menu.findItem(R.id.action_markup);
        if (mExpandLayout.isExpanded())
            //展开，设置向上箭头
            mActionOtherOperate.setIcon(R.drawable.ic_keyboard_arrow_up_white_24dp);
        else
            mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_markup://展开和收缩
                if (!mExpandLayout.isExpanded())
                    //没有展开，但是接下来就是展开，设置向上箭头
                    mActionOtherOperate.setIcon(R.drawable.ic_keyboard_arrow_up_white_24dp);
                else
                    mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
                mExpandLayout.toggle();
                return true;
            case R.id.action_helper:
//                CommonMarkdownActivity.startHelper(this);
                return true;
            case android.R.id.home:
            case R.id.action_done:
//                mEditorFragment.getContent();
                retResult();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        retResult();
    }

    private void retResult() {
        Intent intent = new Intent();
        intent.putExtra(INTENT_EDITOR_CONTENT, getContent());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!mExpandLayout.isExpanded())
                //没有展开，但是接下来就是展开，设置向上箭头
                mActionOtherOperate.setIcon(R.drawable.ic_keyboard_arrow_up_white_24dp);
            else
                mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
            mExpandLayout.toggle();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mEditorFragment.onBackPressed()) return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == SYSTEM_GALLERY) {
            Uri uri = data.getData();
            String[] pojo = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.managedQuery(uri, pojo, null, null, null);
            if (cursor != null) {
//                    ContentResolver cr = this.getContentResolver();
                int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(colunm_index);
                //以上代码获取图片路径
                Uri.fromFile(new File(path));//Uri.decode(imageUri.toString())
//                mEditorFragment.getPerformEditable().perform(R.id.id_shortcut_insert_photo, Uri.fromFile(new File(path)));
            } else {
//                Toast.showShort(this, "图片处理失败");
            }
        }

    }

    /**
     * 插入表格
     */
    private void insertTable() {
        /*View rootView = LayoutInflater.from(this).inflate(R.layout.view_common_input_table_view, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("插入表格")
                .setView(rootView)
                .show();

        TextInputLayout rowNumberHint = (TextInputLayout) rootView.findViewById(R.id.rowNumberHint);
        TextInputLayout columnNumberHint = (TextInputLayout) rootView.findViewById(R.id.columnNumberHint);
        EditText rowNumber = (EditText) rootView.findViewById(R.id.rowNumber);
        EditText columnNumber = (EditText) rootView.findViewById(R.id.columnNumber);


        rootView.findViewById(R.id.sure).setOnClickListener(v -> {
            String rowNumberStr = rowNumber.getText().toString().trim();
            String columnNumberStr = columnNumber.getText().toString().trim();

            if (Check.isEmpty(rowNumberStr)) {
                rowNumberHint.setError("不能为空");
                return;
            }
            if (Check.isEmpty(columnNumberStr)) {
                columnNumberHint.setError("不能为空");
                return;
            }


            if (rowNumberHint.isErrorEnabled())
                rowNumberHint.setErrorEnabled(false);
            if (columnNumberHint.isErrorEnabled())
                columnNumberHint.setErrorEnabled(false);

            mEditorFragment.getPerformEditable().perform(R.id.id_shortcut_grid, Integer.parseInt(rowNumberStr), Integer.parseInt(columnNumberStr));
            dialog.dismiss();
        });

        rootView.findViewById(R.id.cancel).setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();*/
    }

    /**
     * 插入链接
     */
    private void insertLink() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_common_input_link_view, null);

        AlertDialog dialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setTitle("插入链接")
                .setView(rootView)
                .show();

        TextInputLayout titleHint = (TextInputLayout) rootView.findViewById(R.id.inputNameHint);
        TextInputLayout linkHint = (TextInputLayout) rootView.findViewById(R.id.inputHint);
        EditText title = (EditText) rootView.findViewById(R.id.name);
        EditText link = (EditText) rootView.findViewById(R.id.text);


        rootView.findViewById(R.id.sure).setOnClickListener(v -> {
            String titleStr = title.getText().toString().trim();
            String linkStr = link.getText().toString().trim();

/*
            if (Check.isEmpty(titleStr)) {
                titleHint.setError("不能为空");
                return;
            }
            if (Check.isEmpty(linkStr)) {
                linkHint.setError("不能为空");
                return;
            }
*/

            if (titleHint.isErrorEnabled())
                titleHint.setErrorEnabled(false);
            if (linkHint.isErrorEnabled())
                linkHint.setErrorEnabled(false);

//            mEditorFragment.getPerformEditable().perform(R.id.id_shortcut_insert_link, titleStr, linkStr);
            dialog.dismiss();
        });

/*
        rootView.findViewById(R.id.cancel).setOnClickListener(v -> {
            dialog.dismiss();
        });
*/

        dialog.show();
    }

}
