package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.AuthUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.CastUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;
import static com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity.IS_SWITCH_NIGHT_MODE_LOCK;


/**
 * Created by bdpqchen on 17-6-6.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        addPreferencesFromResource(R.xml.settings);
        PreferenceManager.setDefaultValues(this.getActivity(), R.xml.settings, false);
        for (int x = 0; x < getPreferenceScreen().getPreferenceCount(); x++) {
            PreferenceCategory lol = (PreferenceCategory) getPreferenceScreen().getPreference(x);
            for (int y = 0; y < lol.getPreferenceCount(); y++) {
                Preference pref = lol.getPreference(y);
//                pref.setOnPreferenceClickListener(this);
                pref.setOnPreferenceChangeListener(this);
            }
        }

        Preference preference = getPreferenceManager().findPreference(getString(R.string.key_logout));
        if (preference != null) {
            preference.setSummary("当前账户: " + PrefUtil.getAuthUsername());
            preference.setOnPreferenceClickListener(this);
        }


    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.key_logout))) {
            reallyLogout();

        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object obj) {
        LogUtil.dd("on preference change", preference.getKey());
        if (preference.getKey() != null) {
            String key = preference.getKey();
            if (key.equals(getString(R.string.key_night_mode))) {
                LogUtil.d(obj);
                PrefUtil.setIsNightMode(CastUtil.cast2boolean(obj));
                startMySelf();
            } else if (key.equals(getString(R.string.key_always_anon))) {
                PrefUtil.setIsAlwaysAnonymous(CastUtil.cast2boolean(obj));
            } else if (key.equals(getString(R.string.key_slide_back))) {
                PrefUtil.setIsSlideBackMode(CastUtil.cast2boolean(obj));
            } else if (key.equals(getString(R.string.key_habit_hand))) {
                PrefUtil.setHabitHand(CastUtil.cast2int(obj));
            }

        }
        return true;
    }

    private void reallyLogout() {
        DialogUtil.alertDialog(mActivity, "温馨的提示", "真的要登出当前的账户吗？？", "真的", "假的",
                ((materialDialog, dialogAction) -> logout()), null);
    }

    public void startMySelf() {
        HandlerUtil.postDelay(() -> ActivityManager.getActivityManager().recreateAllActivity(SettingsActivity.class), 100);
        ActivityManager.getActivityManager().finishActivity(mActivity);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        Intent intent = mActivity.getIntent();
        intent.putExtra(IS_SWITCH_NIGHT_MODE_LOCK, true);
        startActivity(intent);
    }

    public void logout() {
        AuthUtil.logout();
        ActivityManager.getActivityManager().finishAllActivity();
        Intent intent = new Intent(mActivity, LoginActivity.class);
        intent.putExtra(USERNAME, PrefUtil.getAuthUsername());
        startActivity(intent);

    }


}
