package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageFormatUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImagePickUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PathUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_CAN_ANON;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_FORUM_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_IS_SPECIFY_BOARD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.RESULT_CODE_IMAGE_SELECTED;

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
    @BindView(R.id.ll_select_board)
    LinearLayout mLlSelectBoard;
    @BindView(R.id.ll_select_image)
    LinearLayout mLlSelectImage;
    private ArrayList<String> mBoardNames = new ArrayList<>();

    private int mSelectedBoardId = 0;
    private String mTitle = "";
    private String mContent = "";
    private boolean mIsAnonymous = false;
    private Context mContext;
    private ImageFormatUtil mImageFormatUtil;
    private boolean isPublished = false;
    private int mCanAnon = 0;
    private int mForumId = 0;
    private BoardsModel mBoardsModel = new BoardsModel();

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
        mContext = this;
        mImageFormatUtil = new ImageFormatUtil();
        Intent intent = getIntent();
        mForumId = intent.getIntExtra(INTENT_FORUM_ID, 28);

        //在帖子列表里直接跳进来的
        boolean specifyBoard = intent.getBooleanExtra(INTENT_IS_SPECIFY_BOARD, false);
        if (specifyBoard) {
            mCanAnon = intent.getIntExtra(INTENT_BOARD_CAN_ANON, 0);
            setCbAnonymous();
            mSelectedBoardId = intent.getIntExtra(INTENT_BOARD_ID, 0);
            mTitle = intent.getStringExtra(INTENT_BOARD_TITLE);
            if (mToolbar != null) {
                mToolbar.setTitle("发布帖子|" + mTitle);
            }
            mLlSelectBoard.setVisibility(View.GONE);
        } else {
            mPresenter.getBoardList(mForumId);
            mBoardNames.add("请选择");
            setupSpinner();
        }
        mCbAnonymous.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mIsAnonymous = isChecked;
        });
        mLlSelectImage.setOnClickListener(v -> {
            ImagePickUtil.commonPickImage(this);
        });
    }

    private void setCbAnonymous() {
        if (mCanAnon == 1) {
            if (mCbAnonymous != null)
                mCbAnonymous.setVisibility(View.VISIBLE);
        } else {
            if (mCbAnonymous != null)
                mCbAnonymous.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CODE_IMAGE_SELECTED && resultCode == RESULT_OK) {
            if (data != null) {
                List<Uri> mSelected = Matisse.obtainResult(data);
                mPresenter.uploadImages(PathUtil.getRealPathFromURI(mContext, mSelected.get(0)));
                showProgress("正在添加图片，请稍后..");
                // TODO: 17-6-9  支持多张图片
            }
        }
    }

    @Override
    public void onUploadFailed(String m) {
        LogUtil.dd("error msg upload", m);
        SnackBarUtil.error(this, "图片添加失败\n" + m);
        hideProgress();
    }

    @Override
    public void onUploaded(UploadImageModel model) {
        if (mEtContent != null) {
            if (model != null) {
                mEtContent.setFocusable(true);
                String added = mEtContent.getText() + mImageFormatUtil.getShowImageCode(model.getId());
                mEtContent.setText(added);
                mEtContent.setSelection(mEtContent.getText().length());
            }
        }
        hideProgress();
        SnackBarUtil.normal(this, "图片已添加");
    }

    @Override
    public void onGetBoardList(BoardsModel model) {
        if (model != null && model.getBoards() != null) {
            mBoardsModel = model;
            int size = model.getBoards().size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    BoardsModel.BoardsBean board = model.getBoards().get(i);
                    mBoardNames.add(board.getName());
                }
                setupSpinner();
            }
        }
    }

    @Override
    public void onGetBoardListFailed(String m) {
        mPresenter.getBoardList(mForumId);
    }

    public void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mBoardNames);
        mSpinnerSelectBoard.setAdapter(adapter);
        mSpinnerSelectBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && mBoardsModel != null && mBoardsModel.getBoards() != null) {
                    BoardsModel.BoardsBean board = mBoardsModel.getBoards().get(position - 1);
                    mCanAnon = board.getAnonymous();
                    mSelectedBoardId = board.getId();
                    setCbAnonymous();
                    LogUtil.dd("you have selected the id", String.valueOf(mSelectedBoardId));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LogUtil.dd("onNothingSelected()");
            }
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
        mContent = mImageFormatUtil.replaceImageFormat(mContent);
        if (!isPublished){
            publish();
        }else{
            mAlertDialog = DialogUtil.alertDialog(this, "提示", "你已经成功发布过帖子，还要再发一次吗？", "再次发布", "不要",
                    (materialDialog, dialogAction) -> publish(), null);
        }
        LogUtil.dd("send content", mContent);
    }

    private void publish(){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, mTitle);
        bundle.putString(Constants.CONTENT, mContent);
        bundle.putInt(Constants.BID, mSelectedBoardId);
        bundle.putBoolean(Constants.IS_ANONYMOUS, mIsAnonymous);
        mPresenter.doPublishThread(bundle);
        showProgress("正在发布，请稍后..");
    }

    private void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = DialogUtil.showProgressDialog(this, "提示", msg);
        } else {
            mProgressDialog.show();
        }
    }

    @Override
    public void onPublished() {
        SnackBarUtil.normal(this, "发布成功");
        mProgressDialog.dismiss();
        isPublished = true;

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
        if (isPublished) {
            finishMe();
            return;
        }
        if (mTitle.length() > 0 || mContent.length() > 0) {
            mAlertDialog = DialogUtil.alertDialog(this, "提示", "确定放弃所写的内容吗？？", "放弃", "就不放弃",
                    (materialDialog, dialogAction) -> finishMe(), null);
        } else {
            finishMe();
        }
    }


}
