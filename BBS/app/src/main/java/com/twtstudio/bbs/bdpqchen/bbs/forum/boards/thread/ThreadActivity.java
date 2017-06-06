package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient.BASE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_POST;


/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadActivity extends BaseActivity<ThreadPresenter> implements ThreadContract.View {
    @BindView(R.id.toolbar_thread)
    Toolbar mToolbar;
    @BindView(R.id.rv_thread_post)
    RecyclerView mRvThreadPost;
    @BindView(R.id.pb_thread_loading)
    ProgressBar mPbThreadLoading;
    @BindView(R.id.srl_thread_list)
    SwipeRefreshLayout mSrlThreadList;
    @BindView(R.id.fb_thread_write_post)
    FloatingActionButton mFbThreadWritePost;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.ll_comment)
    LinearLayout mLlComment;
    @BindView(R.id.iv_comment_send)
    ImageView mIvCommentSend;
    @BindView(R.id.iv_comment_out)
    ImageView mIvCommentOut;
    @BindView(R.id.iv_stared_thread)
    ImageView mIvStaredThread;
    @BindView(R.id.iv_star_thread)
    ImageView mIvStarThread;
    @BindView(R.id.tv_dynamic_hint)
    TextView mTvDynamicHint;
    @BindView(R.id.cb_anonymous_comment)
    AppCompatCheckBox mCbAnonymousComment;

    public static final String INTENT_THREAD_FLOOR = "intent_thread_floor";
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar_title_thread)
    TextView mToolbarTitleThread;
    @BindView(R.id.toolbar_title_board)
    TextView mToolbarTitleBoard;

    private String mThreadTitle = "";
    private int mThreadId = 0;
    private int mThreadFloor = 1;
    private Context mContext;
    private PostAdapter mAdapter;
    private String mComment = "";
    private MaterialDialog mProgress;
    private boolean mRefreshing = false;
    private int postPosition = 0;
    private int mReplyId = 0;
    private int mAuthorId = 0;

    private boolean mIsAnonymous = false;
    private boolean mCanAnonymous = false;
    // 用于判断是否可以匿名
    private int mBoardId = 0;
    private String mBoardName = "";
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItemPosition = 0;
    private int mPage = 0;
    private int mEndingPage = 0;
    private boolean mIsLoadingMore;
    private boolean mIsAddingComment = false;
    private int mPostCount = 0;
    private boolean mEnding = false;
    private boolean showingThreadTitle = false;
    private boolean showingBoardName = true;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thread;
    }

    @Override
    protected Toolbar getToolbarView() {
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
        mThreadFloor = intent.getIntExtra(INTENT_THREAD_FLOOR, 1);
        mThreadTitle = intent.getStringExtra(INTENT_THREAD_TITLE);
        mBoardName = intent.getStringExtra(INTENT_BOARD_TITLE);
        mBoardId = intent.getIntExtra(INTENT_BOARD_ID, 0);

        super.onCreate(savedInstanceState);
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        mContext = this;

        mToolbarTitleBoard.setText(TextUtil.getLinkHtml(mBoardName));

        mPresenter.getThread(mThreadId, 0);
        mAdapter = new PostAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        mRvThreadPost.addItemDecoration(new RecyclerViewItemDecoration(5));
        mRvThreadPost.setLayoutManager(mLayoutManager);
        mRvThreadPost.setAdapter(mAdapter);
        mRvThreadPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    if (!mEnding) {
                        mPage++;
                        mIsLoadingMore = true;
                        mPresenter.getThread(mThreadId, mPage);
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
//                LogUtil.d("lastvisi", String.valueOf(lastVisibleItemPosition));
                int d = 300;
                if (lastVisibleItemPosition != 0 && mLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                    if (!showingThreadTitle){
                        //出现标题
                        if (mToolbarTitleBoard != null){
                            YoYo.with(Techniques.SlideOutUp).duration(d).playOn(mToolbarTitleBoard);
//                            mToolbarTitleBoard.setVisibility(View.GONE);
                        }
                        if (mToolbarTitleThread != null){
                            mToolbarTitleThread.setVisibility(View.VISIBLE);
                            YoYo.with(Techniques.SlideInUp).duration(d).playOn(mToolbarTitleThread);
                            mToolbarTitleThread.setText(mThreadTitle);
                        }
                        showingThreadTitle = true;
                        showingBoardName = false;
                    }
                } else {
                    //出现板块名称
                    if (!showingBoardName){
                        if (mToolbarTitleThread != null){
                            YoYo.with(Techniques.SlideOutUp).duration(d).playOn(mToolbarTitleThread);
//                            mToolbarTitleThread.setVisibility(View.GONE);
                        }
                        if (mToolbarTitleBoard != null){
                            mToolbarTitleBoard.setText(TextUtil.getLinkHtml(mBoardName));
                            mToolbarTitleBoard.setVisibility(View.VISIBLE);
                            YoYo.with(Techniques.SlideInUp).duration(d).playOn(mToolbarTitleBoard);
                        }
                        showingBoardName = true;
                        showingThreadTitle = false;
                    }
                }
            }
        });
        mToolbarTitleBoard.setOnClickListener(v -> {
            Intent intent1 = new Intent(mContext, ThreadListActivity.class);
            intent1.putExtra(INTENT_BOARD_ID, mBoardId);
            intent1.putExtra(INTENT_BOARD_TITLE, mBoardName);
            startActivity(intent1);
        });

        mFbThreadWritePost.setOnClickListener(v -> {
            showCommentInput();
            resetReply();
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
        mIvCommentSend.setOnClickListener(v -> sendComment(mReplyId));
        mIvStaredThread.setOnClickListener(v -> {
            if (PrefUtil.hadLogin()) {
                mPresenter.unStarThread(mThreadId);
            }
        });
        mIvStarThread.setOnClickListener(v -> {
            if (PrefUtil.hadLogin()) {
                mPresenter.starThread(mThreadId);
            }
        });
        mSrlThreadList.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlThreadList.setRefreshing(true);
        mSrlThreadList.setOnRefreshListener(() -> {
            if (!mEnding) {
                mRefreshing = true;
                mPage = 0;
                mPresenter.getThread(mThreadId, mPage);
            } else {
                mSrlThreadList.setRefreshing(false);
            }
        });

        mAdapter.setOnItemClickListener((view, position) -> {
            postPosition = position;
            mReplyId = mAdapter.getPostId(position);
            showCommentInput();
        });

        mCbAnonymousComment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mIsAnonymous = isChecked;
        });
    }

    private void showAnonymousOrNot(int canAnonymous) {
        if (mCbAnonymousComment == null) {
            return;
        }
        if (canAnonymous == 1) {
            mCanAnonymous = true;
            mCbAnonymousComment.setVisibility(View.VISIBLE);

            if (PrefUtil.isAlwaysAnonymous()){
                mCbAnonymousComment.setChecked(true);
            }
        } else {
            mCbAnonymousComment.setVisibility(View.GONE);
        }
    }

    private void sendComment(int replyId) {
        mComment = mEtComment.getText().toString();
        if (mEtComment != null && mComment.length() > 0) {
            if (replyId != 0) {
                mComment = mAdapter.comment2reply(postPosition, mComment);
            }
            mPresenter.doComment(mThreadId, mComment, replyId, mIsAnonymous);
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
        resetReply();
        int d = 500;
        YoYo.with(Techniques.SlideInRight)
                .duration(d)
                .playOn(mFbThreadWritePost);
        mFbThreadWritePost.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideOutDown)
                .duration(d)
                .playOn(mLlComment);
        mLlComment.setVisibility(View.GONE);
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    private void showCommentInput() {
        if (PrefUtil.hadLogin()) {
            int d = 300;
            YoYo.with(Techniques.SlideOutLeft)
                    .duration(d)
                    .playOn(mFbThreadWritePost);
            mFbThreadWritePost.setVisibility(View.GONE);
            YoYo.with(Techniques.SlideInUp)
                    .duration(d)
                    .playOn(mLlComment);
            mLlComment.setVisibility(View.VISIBLE);
            mTvDynamicHint.setText(mAdapter.getDynamicHint(postPosition));

            mEtComment.setFocusable(true);
            mEtComment.setFocusableInTouchMode(true);
            mEtComment.requestFocus();
            InputMethodManager imm = (InputMethodManager) mLlComment
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            HandlerUtil.postDelay(() -> {
                if (imm != null) {
                    imm.showSoftInput(mEtComment, 0);
                }
            }, d);
        } else {
            startActivity(new Intent(this, LoginActivity.class));

        }
    }

    private void showStarOrNot(int in_collection) {
        if (in_collection == 1) {
            if (mIvStaredThread != null) {
                mIvStaredThread.setVisibility(View.VISIBLE);
            }
            if (mIvStarThread != null) {
                mIvStarThread.setVisibility(View.GONE);
            }
        } else {
            if (mIvStaredThread != null) {
                mIvStaredThread.setVisibility(View.GONE);
            }
            if (mIvStarThread != null) {
                mIvStarThread.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGotThread(ThreadModel model) {
        int canAnonymous = 0;
        if (model.getThread() != null) {
            mPostCount = model.getThread().getC_post();
            canAnonymous = model.getThread().getAnonymous();
        }
        //只看最后一页
        if (mEnding) {
            if (model.getPost() == null || model.getPost().size() == 0) {
                mEndingPage--;
                mPresenter.getThread(mThreadId, mEndingPage);
            } else {
                mAdapter.refreshList(model.getPost());
//                mEnding = false;
                mEndingPage = 0;
            }
            return;
        }
        if (model.getThread() == null && model.getPost() == null && model.getPost().size() > 0) {
            pageSS();
            return;
        }
        List<ThreadModel.PostBean> postList = new ArrayList<>();
        if (mPage == 0 && model.getThread() != null) {
            mAuthorId = model.getThread().getAuthor_id();
            ThreadModel.ThreadBean thread = model.getThread();
            showStarOrNot(thread.getIn_collection());
            if (thread.getAnonymous() == 1) {
                thread.setAuthor_name("匿名用户");
            }
            ThreadModel.PostBean post = new ThreadModel.PostBean();
            post.setAnonymous(thread.getAnonymous());
            post.setAuthor_name(thread.getAuthor_name());
            post.setAuthor_nickname(thread.getAuthor_nickname());
            post.setContent(thread.getContent());
            post.setFloor(0);
            post.setAuthor_id(thread.getAuthor_id());
            post.setId(thread.getId());
            post.setTitle(thread.getTitle());
            post.setT_create(thread.getT_create());
            post.setT_modify(thread.getT_modify());
            postList.add(post);
            mAdapter.notifyDataSetChanged();
        }
        // TODO: 17-6-2 评论后的刷新
        // 目前只支持刷新第一页
        if (mIsAddingComment) {
            mIsAddingComment = false;
            if (mPage == 0) {
                if (model.getPost() != null) {
                    postList.addAll(model.getPost());
                }
                if (postList.size() > 0) {
                    mAdapter.refreshList(postList);
                }
            }
            return;
        }
        if (mRefreshing) {
            if (model.getPost() != null && model.getPost().size() > 0) {
                postList.addAll(model.getPost());
            }
            mAdapter.refreshList(postList);
            mRefreshing = false;
        } else {
            if (model.getBoard() != null) {
                mBoardId = model.getBoard().getId();
            }
            if (model.getPost() != null && model.getPost().size() > 0) {
                postList.addAll(model.getPost());
            } else {
                pageSS();
            }
            mAdapter.updateThreadPost(postList, mPage);
        }
        setRefreshing(false);
        showAnonymousOrNot(canAnonymous);
        hideProgressBar();
        setCheckBox(false);
    }

    private void toTop() {
        mRvThreadPost.smoothScrollToPosition(0);
    }

    private void toEnd() {
        mEnding = true;
        if (mPostCount > MAX_LENGTH_POST) {
            mEndingPage = mPostCount / MAX_LENGTH_POST;
        } else {
            mEndingPage = 0;
        }
        mPresenter.getThread(mThreadId, mEndingPage);
    }

    private void pageSS() {
        if (mPage != 0) {
            mPage--;
        }
    }

    private void setCheckBox(boolean b) {
        if (mCbAnonymousComment != null) {
            mCbAnonymousComment.setChecked(b);
        }
    }

    @Override
    public void onGetThreadFailed(String m) {
        SnackBarUtil.error(this, m);
        hideProgressBar();
        mRefreshing = false;
        if (mIsLoadingMore) {
            mIsLoadingMore = false;
            mPage--;
        }
        setRefreshing(false);
        pageSS();
        mEnding = false;
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
        if (mPage == 0) {
            mRefreshing = true;
            mIsAddingComment = false;
            mPresenter.getThread(mThreadId, 0);
        } else {
            mIsAddingComment = true;
            mPresenter.getThread(mThreadId, mPage);
        }
        clearCommentData();
    }

    private void clearCommentData() {
        mComment = "";
        mEtComment.setText("");
        resetReply();
    }

    private void resetReply() {
        postPosition = 0;
        mReplyId = 0;
    }

    @Override
    public void onStarFailed(String m) {
        SnackBarUtil.error(this, m);
    }

    @Override
    public void onUnStarFailed(String m) {
        SnackBarUtil.error(this, m);
    }

    @Override
    public void onStarred() {
        SnackBarUtil.normal(this, "收藏成功");
        showStarOrNot(1);
    }

    @Override
    public void onUnStarred() {
        SnackBarUtil.normal(this, "已取消收藏");
        showStarOrNot(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thread_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_thread_share:
                // TODO: 17-6-1 API 对应修改
                String url = BASE + "/forum/thread/" + mThreadId;
                shareText(url);
                break;
            case android.R.id.home:
                finishMe();
                break;
            case R.id.action_to_end:
                if (!mRefreshing) {
                    toEnd();
                }
                break;
            case R.id.action_to_top:
                toTop();
                break;
            case R.id.action_refresh:
                setRefreshing(true);
                mEnding = false;
                mPresenter.getThread(mThreadId, 0);
                break;
        }

        return false;
    }

    //分享文字
    public void shareText(String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "这个是content");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BBS分享的标题");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("text/plain");
//        shareIntent.setType()
//        shareIntent.setType("image/*");
        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    private void hideProgressBar() {
        if (mPbThreadLoading != null) {
            mPbThreadLoading.setVisibility(View.GONE);
        }
    }

    private void setRefreshing(boolean refreshing) {
        if (mSrlThreadList != null) {
            mRefreshing = refreshing;
            mSrlThreadList.setRefreshing(refreshing);
        }
    }
}
