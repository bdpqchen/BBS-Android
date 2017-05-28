package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

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

    private MessageAdapter mAdapter;

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
        mPresenter.getMessageList(0);
    }

    @Override
    public void showMessageList(List<MessageModel> messageList) {
        if (messageList != null && messageList.size() > 0) {
            mAdapter.addList(messageList);
        } else {
            mTvNoMessage.setVisibility(View.VISIBLE);
        }
    }
}
