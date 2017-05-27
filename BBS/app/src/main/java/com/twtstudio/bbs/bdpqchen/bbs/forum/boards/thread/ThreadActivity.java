package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import butterknife.BindView;


/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadActivity extends BaseActivity<ThreadPresenter> implements ThreadContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_thread_post)
    RecyclerView mRvThreadPost;

    public static final String INTENT_THREAD_ID = "intent_thread_id";
    public static final String INTENT_THREAD_TITLE = "intent_thread_title";
    @BindView(R.id.pb_thread_loading)
    ProgressBar mPbThreadLoading;
    @BindView(R.id.srl_thread_list)
    SwipeRefreshLayout mSrlThreadList;
    @BindView(R.id.fb_thread_write_post)
    FloatingActionButton mFbThreadWritePost;
    @BindView(R.id.et_comment)
    TextInputEditText mEtComment;
    @BindView(R.id.ll_comment)
    LinearLayout mLlComment;
    @BindView(R.id.iv_comment_send)
    ImageView mIvCommentSend;
    @BindView(R.id.iv_comment_out)
    ImageView mIvCommentOut;

    private String mThreadTitle = "";
    private int mThreadId = 0;
    private Context mContext;
    private PostAdapter mAdapter;
    private String mComment = "";
    private MaterialDialog mProgress;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thread;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle(mThreadTitle);
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
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mThreadId = intent.getIntExtra(INTENT_THREAD_ID, 0);
        mThreadTitle = intent.getStringExtra(INTENT_THREAD_TITLE);
        super.onCreate(savedInstanceState);
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        mContext = this;
        mPresenter.getThread(mThreadId, 0);
        // TODO: 17-5-27 多页加载
        mAdapter = new PostAdapter(mContext);
        mRvThreadPost.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvThreadPost.addItemDecoration(new RecyclerViewItemDecoration(5));
        mRvThreadPost.setAdapter(mAdapter);
        mRvThreadPost.setItemAnimator(new DefaultItemAnimator());

        mFbThreadWritePost.setOnClickListener(v -> {
            showCommentInput();
        });

        mEtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtComment != null && mEtComment.getText().toString().length() > 0) {
                    mIvCommentOut.setVisibility(View.GONE);
                    mIvCommentSend.setVisibility(View.VISIBLE);
                } else {
                    mIvCommentOut.setVisibility(View.VISIBLE);
                    mIvCommentSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mIvCommentOut.setOnClickListener(v -> showFab());

        mIvCommentSend.setOnClickListener(v -> sendComment());
    }

    private void sendComment() {
        mComment = mEtComment.getText().toString();
        if (mEtComment != null && mComment.length() > 0) {
            mPresenter.doComment(mThreadId, mComment);
            startProgress();
        }
    }

    private void startProgress() {
        mProgress = DialogUtil.showProgressDialog(this, "提示", "正在发送，稍后..");
    }

    private void hideProgress() {
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

    private void showFab() {
        int d = 500;
        YoYo.with(Techniques.SlideInRight)
                .duration(d)
                .playOn(mFbThreadWritePost);
        mFbThreadWritePost.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideOutDown)
                .duration(d)
                .playOn(mLlComment);
        mLlComment.setVisibility(View.GONE);

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mEtComment.setShowSoftInputOnFocus(true);
        }
*/

    }

    private void showCommentInput() {
        int d = 500;
        YoYo.with(Techniques.SlideOutLeft)
                .duration(d)
                .playOn(mFbThreadWritePost);
        mFbThreadWritePost.setVisibility(View.GONE);
        YoYo.with(Techniques.SlideInRight)
                .duration(d)
                .playOn(mLlComment);
        mLlComment.setVisibility(View.VISIBLE);
        // TODO: 17-5-27 自动显示软键盘
    }

    @Override
    public void showThread(ThreadModel model) {
        hideProgressBar();
        mAdapter.setThreadData(model.getThread());
        mAdapter.setPostData(model.getPost());
    }

    @Override
    public void showFailed(String m) {
        SnackBarUtil.error(this, m);
        hideProgressBar();
    }

    @Override
    public void onCommentFailed(String m) {
        hideProgress();
        SnackBarUtil.error(this, m, true);
    }

    @Override
    public void onCommented(PostModel model) {
        hideProgress();
        SnackBarUtil.normal(this, "评论成功");
        showFab();
        // TODO: 17-5-27 刷新列表
    }

    private void hideProgressBar() {
        mPbThreadLoading.setVisibility(View.GONE);
    }


}
