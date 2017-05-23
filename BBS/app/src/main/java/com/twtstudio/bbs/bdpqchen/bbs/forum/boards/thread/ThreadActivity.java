package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.retrox.bbcode.BBCodeParse;
import com.twtstudio.retrox.bbcode.NaiveHtmlUtils;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity.INTENT_THREAD_TITLE;

/**
 * Created by bdpqchen on 17-5-12.
 */

public class ThreadActivity extends BaseActivity<ThreadPresenter> implements ThreadContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.html_content)
    HtmlTextView mHtmlContent;
    @BindView(R.id.html_content_1)
    HtmlTextView mHtmlContent1;
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
        mRvThreadPost.setAdapter(mAdapter);


    }

    @Override
    public void showThread(ThreadModel model) {

        String bbS = "[B]text[/B] [i]text[/i]  [u]text[/u]  [s]text[/s]  [color=#FF0000]Red[/color]  " +
                "[url=http://example.com]Example[/url]\n" +
                "[url]http://example.org[/url]\n" +
                "[img]https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/Laser_Towards_Milky_Ways_Centre.jpg/660px-Laser_Towards_Milky_Ways_Centre.jpg[/img]\n" +
                "[quote=auther]quoted text[/quote]" +
                "[img]http://attach.bbs.miui.com/forum/201402/21/120043wsfuzzuefyasz3fe.jpg[/img]\n" +
                "[img]https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/Gnome-emblem-web.svg/50px-Gnome-emblem-web.svg.png[/img]\n" +
                "[list] [*]Entry 1 [*]Entry 2 [/list]0000" +
                "[size=15] Entry 2 size test [/size] \n" +
                "[list] [*]Entry 3 [*]Entry 4 [/list]" +
                "[list] *Entry 5 *Entry 6 [/list]" +
                "[ol][li]Item 11[/il][li]Item 12[/il][/ol]" +
                "[ul][li]Item 21[/il][li]Item 22[/il][/ul]" +
                "[list][li]Item 31[/il][li]Item 32[/il][/list]" +
                "[code]String TAG = \"tag\"[/code]" +
                "[center]This is some centered text[/center]" +
                "";

        String s = "[align=center] [size=30][b][i]苟利国家生死以，岂因祸福避趋之 [/i][/b][/size][/align]";
//        LogUtil.d(model);
        String content = model.getThread().getContent();
        LogUtil.dd("content", content);
        mTvContent.setText(content);
        mTvContent.setText(content);
        mHtmlContent1.setHtml(content);

        String bbCodeParse = BBCodeParse.bbcode2Html(s);
        NaiveHtmlUtils.GetHtmlImageSrcList(bbCodeParse).forEach(imgUrl -> {
//            LogUtil.d("img", s);
        });


        LogUtil.dd(bbCodeParse);
//        String b = (bbCodeParse, HtmlHttpImageGetter(mHtmlContent));
        mHtmlContent.setHtml(bbCodeParse, new HtmlHttpImageGetter(mHtmlContent));




        updatePostList(model.getPost());
    }

    private void updatePostList(List<ThreadModel.PostBean> postList) {
        mAdapter.addList(postList);
    }

    @Override
    public void showFailed(String m) {
        SnackBarUtil.error(this, m);

    }
}
