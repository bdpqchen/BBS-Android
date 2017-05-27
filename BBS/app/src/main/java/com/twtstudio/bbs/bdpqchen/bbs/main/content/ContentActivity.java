package com.twtstudio.bbs.bdpqchen.bbs.main.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil;
import com.twtstudio.bbs.bdpqchen.bbs.main.TimeUtils;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContentActivity extends BaseActivity<ContentPresenter> implements ContentContract.View {
    @BindView(R.id.item_civ_avatar)
    CircleImageView itemCivAvatar;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.id_recyclerview)
    RecyclerView idRecyclerview;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.create_time)
    TextView createTime;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.status)
    TextView status;
    private LinearLayoutManager linearLayoutManager;
    private ContentAdapter contentAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_index_content;
    }

    @Override
    protected Toolbar getToolbarView() {
        return toolbar;
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
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Intent intent = getIntent();
        String threadid = intent.getStringExtra("threadid");
        contentAdapter = new ContentAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        idRecyclerview.setLayoutManager(linearLayoutManager);
        layoutSwipeRefresh.setRefreshing(true);
        mPresenter.getData(threadid);
        idRecyclerview.setAdapter(contentAdapter);
        layoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(

        ) {
            @Override
            public void onRefresh() {

                mPresenter.getData(threadid);
                layoutSwipeRefresh.setRefreshing(false);

            }
        });
    }

    @Override
    public void showPost(List<ContentModel.DataBean.PostBean> post) {
        if(post.toString()!="[]")
            contentAdapter.refreshList(post);
        else
            status.setText("暂无评论");
        layoutSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showThread(ContentModel.DataBean.ThreadBean thread) {
        ImageUtil.loadAvatarAsBitmapByUid(this,thread.getAuthor_id(),itemCivAvatar);
        author.setText(thread.getAuthor_nickname());
        title.setText(thread.getTitle());
        createTime.setText(TimeUtils.getStandardDate(thread.getT_create()));
        content.setText(thread.getContent());

    }

    @Override
    public void failedToGetContent(String msg) {

    }
}
