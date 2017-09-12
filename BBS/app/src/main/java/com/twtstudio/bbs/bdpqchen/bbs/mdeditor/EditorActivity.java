package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.mdeditor.support.ExpandableLinearLayout;
import com.twtstudio.bbs.bdpqchen.bbs.mdeditor.support.TabIconView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_CONTENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_TOOLBAR_TITLE;

public class EditorActivity extends SupportActivity implements View.OnClickListener, OnContentListener{
    @BindView(R.id.toolbar)
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
    private String mToolbarTitle = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(INTENT_EDITOR_TITLE);
        mToolbarTitle = TextUtil.getEditorToolbarTitle(intent.getIntExtra(INTENT_EDITOR_TOOLBAR_TITLE, 0));
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        mEditorFragment = EditorFragment.getInstance();
        mEditorMarkdownFragment = EditorMarkdownFragment.getInstance();

        initViewPager();
        initTab();
        initActionBar(mIdToolbar);

    }

    private void initActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mToolbarTitle);
        }
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
        mTabIconView.addTab(R.drawable.ic_shortcut_format_bold, R.id.id_shortcut_format_bold, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_italic, R.id.id_shortcut_format_italic, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_1, R.id.id_shortcut_format_header_1, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_2, R.id.id_shortcut_format_header_2, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_3, R.id.id_shortcut_format_header_3, this);
//        mTabIconView.addTab(R.drawable.ic_shortcut_format_quote, R.id.id_shortcut_format_quote, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_xml, R.id.id_shortcut_xml, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_minus, R.id.id_shortcut_minus, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_strikethrough, R.id.id_shortcut_format_strikethrough, this);
//        mTabIconView.addTab(R.drawable.ic_shortcut_grid, R.id.id_shortcut_grid, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_console, R.id.id_shortcut_console, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_4, R.id.id_shortcut_format_header_4, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_5, R.id.id_shortcut_format_header_5, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_6, R.id.id_shortcut_format_header_6, this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.id_shortcut_insert_photo == v.getId()) {
            mEditorFragment.uploadImage();
            return;
        } else if (R.id.id_shortcut_insert_link == v.getId()) {
            //插入链接
            insertLink();
            return;
        }
        /*else if (R.id.id_shortcut_grid == v.getId()) {
            //插入表格
            insertTable();
            return;
        }*/
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
            case android.R.id.home:
            case R.id.action_done:
                retResult();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
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
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 插入链接
     */
    private void insertLink() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_common_input_link_view, null);
        EditText title = (EditText) rootView.findViewById(R.id.name);
        EditText link = (EditText) rootView.findViewById(R.id.text);
        MaterialDialog.SingleButtonCallback callback = (dialog, sequence)->{
            String titleStr = title.getText().toString().trim();
            String linkStr = link.getText().toString().trim();
            mEditorFragment.getPerformEditable().perform(R.id.id_shortcut_insert_link, titleStr, linkStr);
            dialog.dismiss();
        };
        new MaterialDialog.Builder(this)
                .title("插入链接")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .customView(rootView, false)
                .onPositive(callback)
                .positiveText("确定")
                .negativeText("取消")
                .show();

    }

    /**
     * 插入表格
     */
    private void insertTable() {
    /*    View rootView = LayoutInflater.from(this).inflate(R.layout.view_common_input_table_view, null);

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

        dialog.show();
    */}


}
