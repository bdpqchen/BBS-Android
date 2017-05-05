package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.widget.ListView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-5.
 */

public class SettingFragment1 extends BaseFragment<SettingsPresenter> {

    @BindView(R.id.ll_settings)
    ListView mSettingsList;


    private List<SettingsModel1> mDataSets = new ArrayList<>();
    private String[] titles = new String[]{
            "离线接收消息提醒",
            "接收陌生人私信",
            "夜间模式",
            "自动夜间模式"
    };

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_settings_1;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {

        initDataSets();

        mSettingsList.setAdapter(new SettingsFragmentAdapter1(mContext));

    }

    private void initDataSets() {
        // TODO: 17-5-6 将这个直接写入adapter，与listener一起
        for (int i = 0; i < titles.length; i++){
            SettingsModel1 model = new SettingsModel1();
            switch (i){
                case 0 :
                    model.status = PrefUtil.isNoNetworkReceiveNewMessage();
                    break;
                case 1 :
                    model.status = PrefUtil.isReceiveUnknownMessage();
                    break;
                case 2 :
                    model.status = PrefUtil.isNightMode();
                case 3 :
                    model.status = PrefUtil.isAutoNightMode();
                    break;
            }
            model.title = titles[i];
            mDataSets.add(model);
        }

    }
}
