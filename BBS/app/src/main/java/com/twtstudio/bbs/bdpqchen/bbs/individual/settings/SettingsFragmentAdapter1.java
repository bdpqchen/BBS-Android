package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bdpqchen on 17-5-5.
 */

public class SettingsFragmentAdapter1 extends BaseAdapter {

    private Context mContext;
    private Activity mActivity;
    private SettingFragment1 mFragment;

    private List<SettingsModel1> mDataSets = new ArrayList<>();
    private String[] titles = new String[]{
            "离线接收消息提醒",
            "接收陌生人私信",
            "夜间模式",
            "自动夜间模式"
    };

    public SettingsFragmentAdapter1(Context context, Activity act, SettingFragment1 fragment) {
        initDataSets();
        mContext = context;
        mActivity = act;
        mFragment = fragment;

    }

    private void initDataSets() {
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
                    break;
                case 3 :
                    model.status = PrefUtil.isAutoNightMode();
                    break;
            }
            model.title = titles[i];
            mDataSets.add(model);
        }
    }

    @Override
    public int getCount() {
        return mDataSets.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingsModel1 item = (SettingsModel1) getItem(position);
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view_settings_1, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.mSwitchCompat.setText(item.title);
        holder.mSwitchCompat.setChecked(item.status);
        CompoundButton.OnCheckedChangeListener listener = null;
        switch (position) {
            case 0:
                listener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    }
                };
                break;
            case 1:
                listener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                };
                break;
            case 2:
                listener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        PrefUtil.setIsNightMode(isChecked);
//                        ActivityManager.getActivityManager().recreateAllActivity(SettingsActivity.class);
//                        LogUtil.d(PrefUtil.isNightMode());
//                        LogUtil.d("set night mode");
//                        startMySelf();
                    }
                };
                break;
            case 3:
                listener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        PrefUtil.setIsAutoNightMode(isChecked);
                        LogUtil.d("setAutoNight mode");
                    }
                };
                break;

        }
        if (listener != null) {
            holder.mSwitchCompat.setOnCheckedChangeListener(listener);
        }
        return convertView;
    }

    public void startMySelf() {
        //方案1
//        mFragment.pop();
//        ActivityManager.getActivityManager().finishActivity(mActivity);
//        mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        mActivity.startActivity(mActivity.getIntent());

    }

    //不能是private
    static class ViewHolder {

        @BindView(R.id.switch_compat)
        SwitchCompat mSwitchCompat;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
