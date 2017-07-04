package com.twtstudio.bbs.bdpqchen.bbs.individual.letter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.UID;

/**
 * Created by bdpqchen on 17-7-4.
 */

public class LetterActivity extends BaseActivity<LetterPresenter> implements LetterContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_letter)
    RecyclerView mRvLetter;
    private int lastVisibleItemPosition = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_letter;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("用户名");
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

    private LetterAdapter mAdapter;
    private int mPage = 0;
    private int mUid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mUid = intent.getIntExtra(UID, 0);

        mAdapter = new LetterAdapter(mContext);
        mRvLetter.setAdapter(mAdapter);
//        mRvLetter.addItemDecoration(new RecyclerViewItemDecoration(2));
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        mRvLetter.setLayoutManager(manager);
        mPresenter.getLetterList(mUid, mPage);

//        mAdapter.setShowFooter(true);
        mRvLetter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()){
                    LogUtil.dd("onLoadMore");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onGetLetterList(List<LetterModel> modelList) {
        mAdapter.addList(modelList);
        LogUtil.dd("datasize", String.valueOf(modelList.size()));
    }

    @Override
    public void onGetLetterFailed(String m) {
        SnackBarUtil.error(mActivity, m);
    }
}
