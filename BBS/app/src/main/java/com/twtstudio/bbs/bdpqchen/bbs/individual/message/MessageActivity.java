package com.twtstudio.bbs.bdpqchen.bbs.individual.message;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricky on 2017/5/13.
 */

public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_message_list)
    RecyclerView rvMessageList;

    private MessageAdapter mAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.layout_message;
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
        setContentView(R.layout.layout_message);
        ButterKnife.bind(this);
        toolbar.setTitle("我的消息");
        mAdapter = new MessageAdapter(this);
        rvMessageList.setAdapter(mAdapter);
        rvMessageList.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.getMessageList(0);
    }

    @Override
    public void showMessageList(List<MessageModel> messageList) {
        mAdapter.addList(messageList);
    }
}
