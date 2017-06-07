package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.helper.RecyclerViewItemDecoration;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ricky on 2017/5/13.
 */

public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_message_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_no_message)
    TextView mTvNoMessage;
    @BindView(R.id.srl_message)
    SwipeRefreshLayout mSrlMessage;

    private MessageAdapter mAdapter;
    private boolean mRefreshing = false;
    private int lastVisibleItemPosition = 0;
    private int mPage = 0;
    private boolean mIsLoadingMore = false;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.atcivity_message;
    }
    @Override
    protected Toolbar getToolbarView() {
        toolbar.setTitle("我的消息");
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
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        mAdapter = new MessageAdapter(this);
        mAdapter.setShowFooter(true);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(2));
        mPresenter.getMessageList(0);
        setRefreshing(true);
        mSrlMessage.setOnRefreshListener(() -> {
            mPresenter.getMessageList(0);
            mRefreshing = true;
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    mPage++;
                    mPresenter.getMessageList(mPage);
                    mIsLoadingMore = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    @Override
    public void onGetMessageFailed(String m) {
        SnackBarUtil.error(this, m);
        setRefreshing(false);
        pageDecrease();
    }

    @Override
    public void showMessageList(List<MessageModel> messageList) {
        if (mRefreshing) {
            mAdapter.clearAll();
            mRefreshing = false;
        }
        if (messageList != null) {
            int size = messageList.size();
            if (size > 0) {
                List<MessageModel> listNew = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    if (messageList.get(i).getContent_model() != null) {
                        int tag = messageList.get(i).getTag();
                        if (tag == 2 || tag == 3) {
                            listNew.add(messageList.get(i));
                        }
                    }
                }
                if (listNew.size() > 0) {
                    LogUtil.dd("messagelistsize", String.valueOf(listNew.size()));
                    mAdapter.addList(listNew);
                } else {
                    pageDecrease();
                    showNoMessage();
                }

            } else {
                pageDecrease();
                showNoMessage();
            }
        }
        stopRefresh();
    }

    @Override
    public void onCleared() {
        SnackBarUtil.normal(this, "已清空未读消息");
        stopRefresh();
        mRefreshing = true;
        mPresenter.getMessageList(0);
    }

    private void stopRefresh() {
        mRefreshing = false;
        setRefreshing(false);
    }

    @Override
    public void onClearFailed(String msg) {
        SnackBarUtil.error(this, "失败 " + msg);
        stopRefresh();
    }

    void setRefreshing(boolean b){
        if (mSrlMessage != null){
            mSrlMessage.setRefreshing(b);
            mRefreshing = b;
        }
    }

    void showNoMessage(){
        if (mTvNoMessage != null){
            mTvNoMessage.setVisibility(View.VISIBLE);
        }
    }
    void pageDecrease(){
        if (mPage > 0){
            mPage--;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_clear_all_unread:
                mPresenter.doClearUnreadMessage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}