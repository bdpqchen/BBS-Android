package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.tools.MatcherTool;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.CastUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImagePickUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IsUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PathUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.view.BottomToolsView;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.PostModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.EndlessRecyclerOnScrollListener;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient.BASE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_CONTENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_RESULT_AT_USER_NAME;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_RESULT_AT_USER_UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_POST;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_USER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PK_THREAD;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REQUEST_CODE_AT_USER;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REQUEST_CODE_EDITOR;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REQUEST_CODE_IMAGE_SELECTED;


/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadActivity extends BaseActivity implements ThreadContract.View, PostAdapter.OnPostClickListener, View.OnClickListener {
    @BindView(R.id.toolbar_thread)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppbar;
    @BindView(R.id.rv_thread_post)
    RecyclerView mRvThreadPost;
    @BindView(R.id.pb_thread_loading)
    ProgressBar mPbThreadLoading;
    @BindView(R.id.srl_thread_list)
    SwipeRefreshLayout mSrlThreadList;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.ll_comment)
    LinearLayout mLlComment;
    @BindView(R.id.iv_comment_send)
    ImageView mIvCommentSend;
    @BindView(R.id.iv_comment_out)
    ImageView mIvCommentOut;
    @BindView(R.id.tv_dynamic_hint)
    TextView mTvDynamicHint;
    @BindView(R.id.cb_anonymous_comment)
    AppCompatCheckBox mCbAnonymousComment;
    @BindView(R.id.toolbar_title_thread)
    TextView mToolbarTitleThread;
    @BindView(R.id.toolbar_title_board)
    TextView mToolbarTitleBoard;
    @BindView(R.id.iv_select_image)
    ImageView mIvSelectImage;
    @BindView(R.id.iv_open_editor)
    ImageView mIvOpenEditor;
    @BindView(R.id.tv_at_user)
    TextView mTvAtUser;

    public static final String INTENT_THREAD_FLOOR = "intent_thread_floor";
    @BindView(R.id.bottom_tools)
    BottomToolsView mBottomTools;
    private String mThreadTitle = "";
    private int mThreadId = 0;
    private Context mContext;
    private PostAdapter mAdapter;
    private String mComment = "";
    private MaterialDialog mProgress;
    private boolean mRefreshing = false;
    private int postPosition = 0;
    private int mReplyId = 0;
    private boolean mIsAnonymous = false;
    private boolean mCanAnonymous = false;
    private int mBoardId = 0;
    private String mBoardName = "";
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItemPosition = 0;
    private int mPage = 0;
    private boolean mIsLoadingMore;
    private boolean mIsCommentAfter = false;
    private int mPostCount = 0;
    private boolean showingThreadTitle = false;
    private boolean mIsShowingCommentDialog = false;
    private boolean showingBoardName = true;
    private boolean mIsFindingFloor = false;
    private int mFindingPage = 0;
    private int mFindingFloor = 0;
    private int mInputFloor = 0;
    private int mPossibleIndex = 0;

    private boolean mIsFindEnd = false;
    private boolean mFabShowing = true;
    private boolean isLastPage = false;
    private boolean mIsStared = false;
    private boolean mIsLiked = false;
    private ThreadPresenter mPresenter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thread;
    }

    @Override
    protected Toolbar getToolbarView() {
        return mToolbar;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mPresenter = new ThreadPresenter(this);
        mThreadId = intent.getIntExtra(INTENT_THREAD_ID, 0);
        mFindingFloor = intent.getIntExtra(INTENT_THREAD_FLOOR, 0);
        mThreadTitle = intent.getStringExtra(INTENT_THREAD_TITLE);
        mBoardName = intent.getStringExtra(INTENT_BOARD_TITLE);
        mBoardId = intent.getIntExtra(INTENT_BOARD_ID, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        if (mFindingFloor != 0) {
            mIsFindingFloor = true;
        }
        mAdapter = new PostAdapter(mContext, this);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvThreadPost.setAnimation(null);
        mRvThreadPost.addItemDecoration(new RecyclerViewItemDecoration(5));
        mRvThreadPost.setLayoutManager(mLayoutManager);
        mRvThreadPost.setAdapter(mAdapter);
        mRvThreadPost.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore() {
                mPage++;
                mIsLoadingMore = true;
                mPresenter.getThread(mThreadId, mPage);
            }
        });
        mRvThreadPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if (!mIsShowingCommentDialog) {
                    if (dy > 0 && mFabShowing) {
                        mFabShowing = false;
                        hideFab();
                    } else if (dy < 0 && !mFabShowing) {
                        mFabShowing = true;
                        showFab();
                    }
                }
                int d = 300;
                if (lastVisibleItemPosition != 0 && mLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                    if (!showingThreadTitle) {
                        //出现标题
                        if (mToolbarTitleBoard != null) {
                            YoYo.with(Techniques.SlideOutUp).duration(d).playOn(mToolbarTitleBoard);
                        }
                        if (mToolbarTitleThread != null) {
                            YoYo.with(Techniques.SlideInUp).duration(d).playOn(mToolbarTitleThread);
                            mToolbarTitleThread.setText(mThreadTitle);
                        }
                        showingThreadTitle = true;
                        showingBoardName = false;
                    }
                } else {
                    //出现板块名称
                    if (!showingBoardName) {
                        if (mToolbarTitleThread != null) {
                            YoYo.with(Techniques.SlideOutUp).duration(d).playOn(mToolbarTitleThread);
                        }
                        if (mToolbarTitleBoard != null) {
                            mToolbarTitleBoard.setText(TextUtil.getLinkHtml(mBoardName));
                            YoYo.with(Techniques.SlideInUp).duration(d).playOn(mToolbarTitleBoard);
                        }
                        showingBoardName = true;
                        showingThreadTitle = false;
                    }
                }
            }
        });

        mToolbarTitleThread.setOnClickListener(v -> {
            toTop();
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
        mIvCommentOut.setOnClickListener(v -> hideCommentInput());
        mIvCommentSend.setOnClickListener(v -> sendComment(mReplyId));
        mSrlThreadList.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        mSrlThreadList.setRefreshing(true);
        mSrlThreadList.setOnRefreshListener(() -> {
            mRefreshing = true;
            mPage = 0;
            mPresenter.getThread(mThreadId, mPage);
//                mSrlThreadList.setRefreshing(false);
        });
        mCbAnonymousComment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mIsAnonymous = isChecked;
        });
        mIvSelectImage.setOnClickListener(v -> {
            ImagePickUtil.commonPickImage(this);
        });
        mIvOpenEditor.setOnClickListener(v -> {
            startActivityForResult(IntentUtil.toEditor(mContext, mTvDynamicHint.getText().toString(), mEtComment.getText().toString(), 1), REQUEST_CODE_EDITOR);
        });
        mTvAtUser.setOnClickListener(v -> startActivityForResult(IntentUtil.toSearch(mContext, MODE_SEARCH_USER), REQUEST_CODE_AT_USER));
        mPresenter.getThread(mThreadId, 0);
        initBottomTools();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_IMAGE_SELECTED) {
                List<Uri> mSelected = Matisse.obtainResult(data);
                mPresenter.uploadImages(PathUtil.getRealPathFromURI(mContext, mSelected.get(0)));
                startProgress("正在添加图片，请稍后..");
            }
            if (requestCode == REQUEST_CODE_EDITOR) {
                String resultContent = data.getStringExtra(INTENT_EDITOR_CONTENT);
                mEtComment.setText(resultContent);
                mEtComment.setSelection(resultContent.length());
            }
            if (requestCode == REQUEST_CODE_AT_USER) {
                String name = data.getStringExtra(INTENT_RESULT_AT_USER_NAME);
                int uid = data.getIntExtra(INTENT_RESULT_AT_USER_UID, 0);
                TextUtil.addAt2Content(name, mEtComment);
                MatcherTool.addAtName(name, uid);
                LogUtil.dd("I get the hash map data===", String.valueOf(MatcherTool.matchAtUid(name)));
            }
        }
    }

    @Override
    public void onGotThread(ThreadModel model) {
        int canAnonymous = mCanAnonymous ? 1 : 0;
        if (model.getThread() != null) {
            ThreadModel.ThreadBean entity = model.getThread();
            mPostCount = entity.getC_post();
            if (mPage == 0) {
                canAnonymous = model.getBoard().getAnonymous();
            }
            pkTracker(entity.getTitle(), mPage + 1);
        }
        List<ThreadModel.PostBean> postList = new ArrayList<>();
        //将帖主重组成一个回复
        if (mPage == 0 && model.getThread() != null) {
            ThreadModel.ThreadBean thread = model.getThread();
            mIsStared = IsUtil.isStarred(thread.getIn_collection());
            mIsLiked = IsUtil.isLiked(thread.getLiked());
            showStarOrNot();
            if (IsUtil.isAnon(thread.getAnonymous())) {
                thread.setAuthor_name("匿名用户");
            }
            ThreadModel.PostBean post = new ThreadModel.PostBean();
            post.setAnonymous(thread.getAnonymous());
            post.setAuthor_name(thread.getAuthor_name());
            post.setAuthor_nickname(thread.getAuthor_nickname());
            post.setContent(thread.getContent());
            post.setFloor(1);
            post.setAuthor_id(thread.getAuthor_id());
            post.setId(thread.getId());
            post.setTitle(thread.getTitle());
            post.setT_create(thread.getT_create());
            post.setT_modify(thread.getT_modify());
            post.setContent_converted(thread.getContent_converted());
            postList.add(post);
        }
        if (model.getPost() == null || model.getPost().size() == 0) {
            LogUtil.dd("no more post");
            isLastPage = true;
            pageSS();
        }
        //回复后刷新当前页面
        if (mIsCommentAfter) {
            mIsCommentAfter = false;
            if (model.getPost() != null) {
                postList.addAll(model.getPost());
                mAdapter.refreshThisPage(postList, mPage);
            }
        } else {
            if (mRefreshing) {
                mRefreshing = false;
                postList.addAll(model.getPost());
                mAdapter.refreshList(postList);
            } else {
                if (!isLastPage) {
                    postList.addAll(model.getPost());
                }
                mAdapter.updateThreadPost(postList, mPage);
            }
        }
        //跳楼
        if (mIsFindingFloor) {
            if (model.getPost() == null || model.getPost().size() == 0) {
                //到了帖子的尽头
                cannotFindIt();
            } else {
                if (isInside()) {
                    findIt();
                } else {
                    mPresenter.getThread(mThreadId, ++mFindingPage);
                }
            }
            mPage = mFindingPage;
        }
        if (mPage == 0 && model.getBoard() != null) {
            mBoardId = model.getBoard().getId();
            mBoardName = model.getBoard().getName();
            mToolbarTitleBoard.setText(TextUtil.getLinkHtml(mBoardName));
            int finalCanAnonymous = canAnonymous;
            mToolbarTitleBoard.setOnClickListener(v -> {
                startActivity(IntentUtil.toThreadList(mContext, mBoardId, mBoardName, finalCanAnonymous));
            });
        }
        setRefreshing(false);
        showAnonymousOrNot(canAnonymous);
        hideProgressBar();
        setCheckBox(false);
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
    }

    @Override
    public void onCommentFailed(String m) {
        hideProgress();
        SnackBarUtil.error(this, m, true);
    }

    @Override
    public void onCommented(PostModel model) {
        hideProgress();
        hideCommentInput();
        clearCommentData();
        SnackBarUtil.normal(this, "评论成功");
        mIsCommentAfter = true;
        mPresenter.getThread(mThreadId, mPage);
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
        mIsStared = true;
        SnackBarUtil.normal(this, "收藏成功");
        showStarOrNot();
    }

    @Override
    public void onUnStarred() {
        mIsStared = false;
        SnackBarUtil.normal(this, "已取消收藏");
        showStarOrNot();
    }

    @Override
    public void onUploadFailed(String m) {
        SnackBarUtil.error(this, "图片添加失败\n" + m);
        hideProgress();
    }

    @Override
    public void onUploaded(UploadImageModel model) {
        if (mEtComment != null && model != null) {
            TextUtil.addImg2Content(model.getId(), mEtComment);
            SnackBarUtil.normal(this, "图片已添加");
        }
        hideProgress();
    }

    @Override
    public void onLike(BaseModel model) {
    }

    @Override
    public void onLikeFailed(String m, int pos, boolean isLike) {
        SnackBarUtil.notice(this, m);
        if (pos > 0) {
            mAdapter.likeItem(pos, !isLike);
        } else {
            mIsLiked = false;
            showLikedOrNot();
        }
    }

    @Override
    public void onUnlike(BaseModel entity) {
    }

    @Override
    public void onUnlikeFailed(String m, int position, boolean isLike) {
        SnackBarUtil.notice(this, m);
        LogUtil.dd("position is", String.valueOf(position));
        if (position > 0) {
            mAdapter.likeItem(position, !isLike);
        } else {
            mIsLiked = true;
            showLikedOrNot();
        }
    }

    private void initBottomTools() {
        int[] reses = new int[]{R.drawable.ic_star_white_24dp, R.drawable.ic_share_white_24dp, R.drawable.ic_vertical_align_top_white_24dp,
                R.drawable.ic_clear_white_24dp, R.drawable.ic_import_export_black_24dp, R.drawable.ic_thumb_up_white_24dp, R.drawable.ic_sms_black_24dp};
        mBottomTools.addTabs(reses, this);
        mBottomTools.changeIconPadding(1, 2);
        mBottomTools.changeIconPadding(5, 4);
        mBottomTools.changeIconPadding(6, 4);
        mBottomTools.changeIconPadding(4, 2);
    }

    private void stopFinding() {
        mFindingFloor = 0;
        mIsFindingFloor = false;
        mRvThreadPost.scrollToPosition(mPossibleIndex);
        setRefreshing(false);
        hideProgress();
    }

    private void cannotFindIt() {
        if (!mIsFindEnd) {
            SnackBarUtil.notice(this, "该楼层不存在,可能已经被删除\n已经帮你跳转到附近楼层", true);
        } else {
            mIsFindEnd = false;
        }
        stopFinding();
    }

    private void findIt() {
        stopFinding();
    }

    private void findFloor(int floor) {
        mFindingFloor = floor;
        if (isInside()) {
            mRvThreadPost.scrollToPosition(mPossibleIndex);
            mPage = mFindingPage;
            hideProgress();
        } else {
            mIsFindingFloor = true;
            mFindingPage = ++mPage;
            mPresenter.getThread(mThreadId, mFindingPage);
        }
    }

    private boolean isInside() {
        mPossibleIndex = 0;
        boolean isFindIt = false;
        if (mAdapter.getPostList() == null) {
            return false;
        }
        List<ThreadModel.PostBean> posts = mAdapter.getPostList();
        int size = posts.size();
        int start = 0;
        if (mIsFindingFloor) {
            start = mFindingPage * MAX_LENGTH_POST;
        }
        if (size > 0) {
            for (int i = start; i < size; i++) {
                int floor = posts.get(i).getFloor();
                if (floor == mFindingFloor) {
                    isFindIt = true;
                    LogUtil.dd("find it", String.valueOf(i));
                    mPossibleIndex = i;
                    break;
                }
                if (floor > mFindingFloor) {
                    mPossibleIndex = i;
                    break;
                }
                mPossibleIndex = i;
            }
        }
        return isFindIt;
    }

    private void toTop() {
        if (mPage == 0) {
            mRvThreadPost.smoothScrollToPosition(0);
        } else {
            mRvThreadPost.scrollToPosition(0);
        }
    }

    private void showAnonymousOrNot(int canAnonymous) {
        if (mCbAnonymousComment == null) {
            return;
        }
        if (canAnonymous == 1) {
            mCanAnonymous = true;
            if (PrefUtil.isAlwaysAnonymous()) {
                HandlerUtil.postDelay(() -> {
                    if (mCbAnonymousComment != null) {
                        mCbAnonymousComment.setChecked(true);
                    }
                }, 600);
            }
            mCbAnonymousComment.setVisibility(View.VISIBLE);
        } else {
            mCbAnonymousComment.setVisibility(View.GONE);
        }
    }

    private void sendComment(int replyId) {
        if (mEtComment == null) {
            return;
        }
        mComment = MatcherTool.getAtContent(mEtComment.getText().toString());
        if (mComment.length() > 0) {
            if (replyId != 0) {
                mComment = mAdapter.comment2reply(postPosition, mComment);
            }
            LogUtil.dd("final comment content", mComment);
            mPresenter.doComment(mThreadId, mComment, replyId, mIsAnonymous);
            startProgress("正在发送，稍后..");
        }
    }

    private void startProgress(String msg) {
        mProgress = DialogUtil.showProgressDialog(this, msg);
    }

    private void hideProgress() {
//        LogUtil.dd("hideProgress");
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

    private void hideFab() {
        bottomToolsBehavior(Techniques.SlideOutUp);
    }

    private void bottomToolsBehavior(Techniques technique) {
//        YoYo.with(technique).duration(500).playOn(m);
    }

    private void showFab() {
        bottomToolsBehavior(Techniques.SlideInDown);
    }

    private void hideCommentInput() {
        resetReply();
        if (mBottomTools != null) {
            mBottomTools.setVisibility(View.VISIBLE);
        }
        if (mLlComment != null) {
            mLlComment.setVisibility(View.GONE);
        }
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        mIsShowingCommentDialog = false;
    }

    private void showCommentInput() {
        mIsShowingCommentDialog = true;
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
        }, 100);

    }

    private void showStarOrNot() {
        final int iconIndex = 0;
        if (mIsStared) {
            mBottomTools.changeIconTint(iconIndex, ResourceUtil.getColor(mContext, R.color.material_yellow_600));
        } else {
            mBottomTools.resetIconTint(iconIndex);
        }
    }

    private void starThread() {
        if (mIsStared) {
            mPresenter.unStarThread(mThreadId);
        } else {
            mPresenter.starThread(mThreadId);
        }
    }

    private void showLikedOrNot() {
        final int iconIndex = 5;
        if (mIsLiked) {
            mBottomTools.changeIconTint(iconIndex, ResourceUtil.getColor(mContext, R.color.colorPrimaryCopy));
        } else {
            mBottomTools.resetIconTint(iconIndex);
        }
    }

    private void setCheckBox(boolean b) {
        if (mCbAnonymousComment != null) {
            mCbAnonymousComment.setChecked(b);
        }
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

    private void clearCommentData() {
        mComment = "";
        mEtComment.setText("");
        resetReply();
    }

    private void resetReply() {
        postPosition = 0;
        mReplyId = 0;
    }

    private void pageSS() {
        if (mPage != 0) {
            mPage--;
        }
    }

    private void shareText() {
        String url = BASE + "/forum/thread/" + mThreadId;
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

    private void pkTracker(String title, int page) {
        getTrackerHelper()
                .screen(PK_THREAD + mThreadId + "/page/" + page + "/")
                .title(title).with(getTracker());
    }

    @Override
    public void onLikeClick(int position, boolean isLike, boolean isPost) {
        mPresenter.like(mAdapter.getPostId(position), position, isLike, isPost);
        mAdapter.likeItem(position, isLike);
    }

    @Override
    public void onReplyClick(int position) {
        postPosition = position;
        mReplyId = mAdapter.getPostId(position);
        showCommentInput();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        LogUtil.dd("bootom tools position clicked", String.valueOf(position));
        switch (position) {
            case 0:
                starThread();
                break;
            case 1:
                shareText();
                break;
            case 2:
                toTop();
                break;
            case 3:
                finishMe();
                break;
            case 4:
                jumpFloor();
                break;
            case 5:
                like();
                break;
            case 6:
                showCommentInput();
                break;
        }
    }

    private void like() {
        mIsLiked = !mIsLiked;
        showLikedOrNot();
        mPresenter.like(mThreadId, 0, mIsLiked, true);
    }

    private void jumpFloor() {
        if (!mRefreshing) {
            DialogUtil.inputDialog(mContext,
                    "输入楼层,最大可能是" + mPostCount + "左右",
                    (dialog, input) -> {
                        mInputFloor = CastUtil.parse2intWithMax(input.toString());
                        if (mInputFloor != 0) {
                            mFindingPage = mPage;
                            startProgress("正在前往..");
                            findFloor(mInputFloor);
                        }
                    });
        }
    }


}
