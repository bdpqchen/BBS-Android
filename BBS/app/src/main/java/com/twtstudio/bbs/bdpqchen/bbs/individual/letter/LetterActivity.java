package com.twtstudio.bbs.bdpqchen.bbs.individual.letter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.BuildConfig;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MAX_LENGTH_Letter;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MIN_LENGTH_LETTER_CONTENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;

/**
 * Created by bdpqchen on 17-7-4.
 */

public class LetterActivity extends BaseActivity implements LetterContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_letter)
    RecyclerView mRvLetter;
    @BindView(R.id.et_letter)
    EditText mEtLetter;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    private LetterPresenter mPresenter;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_letter;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle(" ");
        return mToolbar;
    }

    @Override
    protected LetterPresenter getPresenter() {
        return mPresenter;
    }

    private static final int SEC = (BuildConfig.DEBUG ? 3 : 600) * 1000;
    public static final int REFRESH = 1;

    private LetterAdapter mAdapter;
    private int mPage = 0;
    private int mUid = 0;
    private boolean isJustInto = true;
    private boolean isTopping = false;
    private String mContent = "";
    LinearLayoutManager manager;
    private int lastVisibleItemPosition = 0;
    private int mInterval = SEC;
    private int mOldTime = 0;
    private int mNewDataSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setColor(this, Color.WHITE,0);
        enableLightStatusBarMode(true);
        mPresenter= new LetterPresenter(this);
        Intent intent = getIntent();
        mUid = intent.getIntExtra(UID, 0);
        mToolbar.setTitle(intent.getStringExtra(USERNAME));
        mAdapter = new LetterAdapter(mContext);
        mRvLetter.setAdapter(mAdapter);
        manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        mRvLetter.setLayoutManager(manager);
        mRvLetter.setNestedScrollingEnabled(false);
        mPresenter.getLetterList(mUid, mPage, 0);
        mAdapter.setShowFooter(true);
        mTvSend.setOnClickListener(v -> {
            mPresenter.sendLetter(mUid, mContent);
        });
        mRvLetter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
//                    LogUtil.dd("onLoadMore");
                    if (!isTopping) {
                        mPresenter.getLetterList(mUid, ++mPage, 0);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            }
        });

        mEtLetter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mContent = mEtLetter.getText().toString();
                int length = mContent.length();
                mTvSend.setAlpha(getSendBtnTextAlpha(length));
                updateSenderClickable(length >= MIN_LENGTH_LETTER_CONTENT);
            }
        });

        KeyboardVisibilityEvent.setEventListener(mActivity, isOpen -> {
            if (isOpen) {
                mRvLetter.scrollToPosition(0);
            }
        });


    }

    private float getSendBtnTextAlpha(int length) {
        return length < MIN_LENGTH_LETTER_CONTENT ? 0.2f : 1.0f;
    }

    private void updateSenderClickable(boolean sendable) {
        mTvSend.setClickable(sendable);
    }

    @Override
    public void onGetLetterList(List<LetterModel> modelList) {
        if (modelList != null && modelList.size() != 0) {
            if (mPage == 0) {
                mAdapter.clearAll();
            }
            if (mPage == 0 && modelList.size() < MAX_LENGTH_Letter) {
                mAdapter.setNotEnoughOnePage(true);
            }
            if (mNewDataSize > 0) {
                mAdapter.addListWithRedundancy(modelList);
            } else {
                if (isJustInto) {
                    mOldTime = modelList.get(0).getT_create();
                }
                mAdapter.addList(modelList, mPage);
            }
        } else {
            mPage--;
            isTopping = true;
            mAdapter.setTopping(true);
        }
        if (isJustInto) {
            isJustInto = false;
            mRvLetter.scrollToPosition(0);
            HandlerUtil.postDelay(() -> {
                mPresenter.getLetterList(mUid, 0, REFRESH);
            }, SEC);
        }
    }

    @Override
    public void onRefreshList(List<LetterModel> modelList) {
        List<LetterModel> list = new ArrayList<>();
        if (modelList != null) {
            int size = modelList.size();
            for (int i = 0; i < size; i++) {
                if (modelList.get(i).getT_create() > mOldTime) {
                    list.add(modelList.get(i));
                } else {
                    break;
                }
            }
        }
        int size = list.size();
        if (size > 0) {
            mAdapter.addFirst(list);
            mNewDataSize = size;
            mInterval = SEC;
            mOldTime = list.get(0).getT_create();
        } else {
            mInterval *= 2;
        }

        HandlerUtil.postDelay(() -> {
            mPresenter.getLetterList(mUid, 0, REFRESH);
        }, mInterval);

    }

    @Override
    public void onGetLetterFailed(String m) {
        SnackBarUtil.error(mActivity, m);
    }

    @Override
    public void onSend(BaseModel model) {
        mPresenter.getLetterList(mUid, 0, REFRESH);
        mEtLetter.getText().clear();
    }

    @Override
    public void onSendFailed(String m) {
        SnackBarUtil.error(mActivity, m);
        mPresenter.getLetterList(mUid, 0, REFRESH);
    }
}
