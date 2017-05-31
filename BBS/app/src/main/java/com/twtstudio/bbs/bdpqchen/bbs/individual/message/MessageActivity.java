package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    RecyclerView rvMessageList;
    @BindView(R.id.tv_no_message)
    TextView mTvNoMessage;
    @BindView(R.id.srl_message)
    SwipeRefreshLayout mSrlMessage;

    private MessageAdapter mAdapter;
    private boolean mRefreshing = false;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.layout_message;
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
        rvMessageList.setAdapter(mAdapter);
        rvMessageList.setLayoutManager(new LinearLayoutManager(this));
        rvMessageList.addItemDecoration(new RecyclerViewItemDecoration(10));
        mPresenter.getMessageList(0);
        mSrlMessage.setRefreshing(true);
        mSrlMessage.setOnRefreshListener(() -> {
            mPresenter.getMessageList(0);
            mRefreshing = true;
        });
    }

    @Override
    public void onGetMessageFailed(String m) {
        SnackBarUtil.error(this, m);
        mSrlMessage.setRefreshing(false);
    }

    @Override
    public void showMessageList(List<MessageModel> messageList) {
        // TODO: 17-5-29 设定为已读消息
//        mPresenter.doClearUnreadMessage();
        if (mRefreshing) {
            mAdapter.clearAll();
        }
        if (messageList != null) {
            int size = messageList.size();
            if (size != 0) {
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
                    mTvNoMessage.setVisibility(View.VISIBLE);
                }

            } else {
                mTvNoMessage.setVisibility(View.VISIBLE);
            }
        }
        stopRefresh();

    }

    @Override
    public void onCleared() {
        SnackBarUtil.normal(this, "已清空未读消息");
        mPresenter.getMessageList(0);
        stopRefresh();
    }

    private void stopRefresh() {
        mRefreshing = false;
        mSrlMessage.setRefreshing(true);
    }

    @Override
    public void onClearFailed(String msg) {
        SnackBarUtil.error(this, "失败 " + msg);
        stopRefresh();
    }
}