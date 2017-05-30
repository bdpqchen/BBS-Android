package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.ArrayList;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.CONTENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_IDS;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_NAMES;

/**
 * Created by bdpqchen on 17-5-27.
 */

public class CreateThreadActivity extends BaseActivity<CreateThreadPresenter> implements CreateThreadContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinner_select_board)
    AppCompatSpinner mSpinnerSelectBoard;
    @BindView(R.id.et_title)
    TextInputEditText mEtTitle;
    @BindView(R.id.et_content)
    TextInputEditText mEtContent;
    MaterialDialog mAlertDialog;
    MaterialDialog mProgressDialog;
    @BindView(R.id.cb_anonymous)
    AppCompatCheckBox mCbAnonymous;
    private ArrayList<String> mBoardNames;
    private ArrayList<Integer> mBoardIds;

    private int mSelectedBoardId = 0;
    private String mTitle = "";
    private String mContent = "";
    private boolean mIsAnonymous = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_thread;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("发布帖子");
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected boolean isSupportNightMode() {
        return true;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        // 本页面不支持滑动返回，因为，怕用户滑出去了
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mBoardNames = intent.getStringArrayListExtra(INTENT_BOARD_NAMES);
        mBoardIds = intent.getIntegerArrayListExtra(INTENT_BOARD_IDS);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mBoardNames);
        mSpinnerSelectBoard.setAdapter(adapter);
        mSpinnerSelectBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedBoardId = mBoardIds.get(position);
                if (mSelectedBoardId == 193) {
                    //匿名板块 anonymous
                    //// TODO: 17-5-29 匿名板块逻辑
                    mCbAnonymous.setVisibility(View.VISIBLE);
                }else{
                    mCbAnonymous.setVisibility(View.GONE);
                }
                LogUtil.dd("you have selected the id", String.valueOf(mSelectedBoardId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mCbAnonymous.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mIsAnonymous = isChecked;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_create_thread, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_thread:
                checkInput();
                break;
            case android.R.id.home:
                isDangerExit();
                break;
        }
        return false;
    }

    private void setupData() {
        mTitle = mEtTitle.getText().toString();
        mContent = mEtContent.getText().toString();
    }

    private void checkInput() {
        String err = "不能为空啊大哥";
        setupData();
        if (mTitle.length() == 0) {
            mEtTitle.setError(err);
            return;
        } else if (mContent.length() == 0) {
            mEtContent.setError(err);
            return;
        }

        if (mSelectedBoardId == 0) {
            SnackBarUtil.error(this, "你还没有选择板块");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, mTitle);
        bundle.putString(Constants.CONTENT, mContent);
        bundle.putInt(Constants.BID, mSelectedBoardId);
        bundle.putBoolean(Constants.IS_ANONYMOUS, mIsAnonymous);
        mPresenter.doPublishThread(bundle);
        mProgressDialog = DialogUtil.showProgressDialog(this, "提示", "正在发布，请稍后..");
    }


    @Override
    public void onPublished() {
        SnackBarUtil.normal(this, "发布成功");
        HandlerUtil.postDelay(this::finishMe, 2000);
        mProgressDialog.dismiss();
    }

    @Override
    public void onPublishFailed(String msg) {
        SnackBarUtil.error(this, msg, true);
        mProgressDialog.dismiss();
    }

    @Override
    public void onBackPressedSupport() {
        isDangerExit();
    }

    private void isDangerExit() {
        LogUtil.dd("isDangerExit()");
        setupData();
        if (mTitle.length() > 0 || mContent.length() > 0) {
            mAlertDialog = DialogUtil.alertDialog(this, "提示", "确定放弃所写的内容吗？？", "放弃", "就不放弃",
                    (materialDialog, dialogAction) -> finishMe(), null);
        } else {
            finishMe();
        }
    }


}
