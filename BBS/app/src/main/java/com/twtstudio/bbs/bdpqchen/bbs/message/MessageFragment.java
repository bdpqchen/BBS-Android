package com.twtstudio.bbs.bdpqchen.bbs.message;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainTabAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**@deprecated
 * Created by bdpqchen on 17-5-3.
 */

public class MessageFragment extends SimpleFragment {


    @BindView(R.id.toolbar_message)
    Toolbar mToolbar;
    @BindView(R.id.tl_message)
    TabLayout mTlMessage;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.vp_message)
    ViewPager mVpMessage;

    public List<MessageModel> mList = new ArrayList<>();

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getPerMainFragmentLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initFragments() {
        mToolbar.setTitle("消息");
        MainTabAdapter tabAdapter = new MainTabAdapter(getFragmentManager(), mContext);
        mVpMessage.setAdapter(tabAdapter);
        mTlMessage.setupWithViewPager(mVpMessage);
        mAppbar.setExpanded(false);


    }

    public List<MessageModel> getMessageList(){
        return mList;
    }




}
