package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.StampUtil;
import com.twtstudio.retrox.bbcode.BBCodeParse;
import com.twtstudio.retrox.bbcode.NaiveHtmlUtils;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadActivity extends BaseActivity<ThreadPresenter> implements ThreadContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.civ_avatar_thread)
    CircleImageView mCivAvatarThread;
    @BindView(R.id.tv_username_thread)
    TextView mTvUsernameThread;
    @BindView(R.id.tv_level_thread)
    TextView mTvLevelThread;
    @BindView(R.id.tv_datetime_thread)
    TextView mTvDatetimeThread;
    @BindView(R.id.iv_star_thread)
    ImageView mIvStarThread;
    @BindView(R.id.iv_stared_thread)
    ImageView mIvStaredThread;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_thread_post)
    RecyclerView mRvThreadPost;
    @BindView(R.id.htv_content)
    HtmlTextView mHtvContent;

    public static final String INTENT_THREAD_ID = "intent_thread_id";
    public static final String INTENT_THREAD_TITLE = "intent_thread_title";
    @BindView(R.id.pb_thread_loading)
    ProgressBar mPbThreadLoading;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;


    private String mThreadTitle = "";
    private int mThreadId = 0;
    private Context mContext;
    private PostAdapter mAdapter;


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
        mAdapter = new PostAdapter(mContext);
        mRvThreadPost.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvThreadPost.addItemDecoration(new RecyclerViewItemDecoration(5));
        mRvThreadPost.setNestedScrollingEnabled(false);
        mRvThreadPost.setAdapter(mAdapter);
        mRvThreadPost.setItemAnimator(new DefaultItemAnimator());
//        mScrollView.smoothScrollTo(0, 0);

    }

    @Override
    public void showThread(ThreadModel model) {
        hideProgressBar();
        ThreadModel.ThreadBean thread = model.getThread();
        mTvUsernameThread.setText(thread.getAuthor_nickname());
        mTvDatetimeThread.setText(StampUtil.getDateByStamp(thread.getT_create()));
        mTvTitle.setText(thread.getTitle());
        ImageUtil.loadAvatarByUid(mContext, thread.getAuthor_id(), mCivAvatarThread);

        String htmlStr = BBCodeParse.bbcode2Html(thread.getContent());
        /*NaiveHtmlUtils.GetHtmlImageSrcList(htmlStr).forEach(imgUrl -> {
            LogUtil.dd("image url ", imgUrl);
        });*/

        mHtvContent.setHtml(htmlStr, new HtmlHttpImageGetter(mHtvContent));

        updatePostList(model.getPost());
    }

    private void updatePostList(List<ThreadModel.PostBean> postList) {
        hideProgressBar();
        mAdapter.addList(postList);
    }

    @Override
    public void showFailed(String m) {
        SnackBarUtil.error(this, m);
        hideProgressBar();
    }

    private void hideProgressBar() {
        mPbThreadLoading.setVisibility(View.GONE);
    }
}
