package com.twtstudio.bbs.bdpqchen.bbs.main.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    private LinearLayoutManager linearLayoutManager;
    private ContentAdapter contentAdapter;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_index_content;
    }

    @Override
    protected Toolbar getToolbarView() {
        return null;
    }

    @Override
    protected boolean isShowBackArrow() {
        return false;
    }

    @Override
    protected boolean isSupportNightMode() {
        return false;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String threadid = intent.getStringExtra("threadid");
        contentAdapter=new ContentAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        idRecyclerview.setLayoutManager(linearLayoutManager);
        layoutSwipeRefresh.setRefreshing(true);
        mPresenter.getData(threadid);
        idRecyclerview.setAdapter(contentAdapter);
    }

    @Override
    public void showPost(List<ContentModel.DataBean.PostBean> post) {
        contentAdapter.refreshList(post);
        layoutSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showThread(ContentModel.DataBean.ThreadBean thread) {
        String avatarurl = "https://bbs.twtstudio.com/api/user/" + thread.getAuthor_id() + "/avatar";
        ImageUtil.loadItemAvatarHome(this, itemCivAvatar, avatarurl);
        author.setText(thread.getAuthor_nickname());
        title.setText(thread.getTitle());
        createTime.setText(TimeUtils.getStandardDate(thread.getT_create()));
        content.setText(thread.getContent());

    }

    @Override
    public void failedToGetContent(String msg) {

    }
}
