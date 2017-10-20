package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

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
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_UNREAD;

/**
 * Created by bdpqchen on 2017/5/28.
 */

public class MessageActivity extends BaseActivity implements MessageContract.View {


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
    private LinearLayoutManager mLayoutManager;
    private boolean autoClear = true;
    private MessagePresenter mPresenter;
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
    protected MessagePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MessagePresenter(this);
        mAdapter = new MessageAdapter(this, mPresenter);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 2 >= mAdapter.getItemCount()) {
                    mPage++;
                    mPresenter.getMessageList(mPage);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        if (getIntent().getIntExtra(INTENT_UNREAD, 0) > 0){
            mPresenter.doClearUnreadMessage();
        }

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
            mAdapter.addList(messageList);
        }else{
            pageDecrease();
            showNoMessage();
        }
        stopRefresh();
    }

    @Override
    public void onCleared() {
        mRefreshing = true;
        if (!autoClear){
            autoClear = false;
            SnackBarUtil.normal(this, "已清空未读消息");
            mPresenter.getMessageList(0);
        }
    }

    private void stopRefresh() {
        mRefreshing = false;
        setRefreshing(false);
    }

    @Override
    public void onClearFailed(String msg) {
        if (!autoClear){
            autoClear = false;
            SnackBarUtil.error(this, "失败 " + msg);
        }
        stopRefresh();
    }

    @Override
    public void onConfirmFriendFailed(String m, int position) {
        SnackBarUtil.error(mActivity, m);
    }

    @Override
    public void onConfirmFriendSuccess(BaseModel model, int position, int isGrant) {
        String m = "成功建立好友关系";
        if (isGrant == 0){
            m = "已残忍拒绝";
            isGrant = -1;
        }
        SnackBarUtil.notice(mActivity, m);
        mAdapter.updateRead(position, isGrant);
        mAdapter.updateConfirm(position, 1);
        mAdapter.notifyDataSetChanged();

    }

    void setRefreshing(boolean b){
        if (mSrlMessage != null){
            mSrlMessage.setRefreshing(b);
            mRefreshing = b;
        }
    }

    void showNoMessage(){
        if (mTvNoMessage != null && mPage == 0){
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
//        getMenuInflater().inflate(R.menu.menu_message_delete, menu);
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