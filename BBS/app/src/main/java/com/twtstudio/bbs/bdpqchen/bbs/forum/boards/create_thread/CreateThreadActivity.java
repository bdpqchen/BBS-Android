package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-27.
 */

public class CreateThreadActivity extends BaseActivity<CreateThreadPresenter> implements CreateThreadContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinner_select_board)
    AppCompatSpinner mSpinnerSelectBoard;

    private ArrayList<String> mBoardNames;
    private ArrayList<Integer> mBoardIds;

    private int mSelectedBoardId = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_thread;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("发布帖子");
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
        // 本页面不支持滑动返回，因为，怕用户滑出去了
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mBoardNames = intent.getStringArrayListExtra(BoardsActivity.INTENT_BOARD_NAMES);
        mBoardIds = intent.getIntegerArrayListExtra(BoardsActivity.INTENT_BOARD_IDS);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mBoardNames);
        mSpinnerSelectBoard.setAdapter(adapter);
        mSpinnerSelectBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedBoardId = mBoardIds.get(position);
                LogUtil.dd("you have selected the id", String.valueOf(mSelectedBoardId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
