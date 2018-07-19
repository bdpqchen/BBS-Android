package com.twtstudio.bbs.bdpqchen.bbs.individual.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager;
import com.twtstudio.bbs.bdpqchen.bbs.commons.tools.AuthTool;
import com.twtstudio.bbs.bdpqchen.bbs.commons.tools.UpdateTool;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.CastUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.HandlerUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

import static com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity.IS_SWITCH_NIGHT_MODE_LOCK;


/**
 * Created by bdpqchen on 17-6-6.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private AppCompatActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        addPreferencesFromResource(R.xml.settings);
        PreferenceManager.setDefaultValues(this.getActivity(), R.xml.settings, false);
        for (int x = 0; x < getPreferenceScreen().getPreferenceCount(); x++) {
            PreferenceCategory lol = (PreferenceCategory) getPreferenceScreen().getPreference(x);
            for (int y = 0; y < lol.getPreferenceCount(); y++) {
                Preference pref = lol.getPreference(y);
                pref.setOnPreferenceClickListener(this);
                pref.setOnPreferenceChangeListener(this);
            }
        }

        Preference preference = getPreferenceManager().findPreference(getString(R.string.key_logout));
        if (preference != null) {
            preference.setTitle(PrefUtil.getAuthUsername());
            preference.setOnPreferenceClickListener(this);
        }

    }

    /**
     * Method will be called when item onClicked
     *
     * @param preference
     * @return
     */
    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.key_logout))) {
            reallyLogout();
        } else if (key.equals(getString(R.string.key_check_update))) {
            checkUpdate();
        } else if (key.equals(getString(R.string.key_feedback))) {
            startActivity(IntentUtil.toThread(mActivity, 155651));
        }
        return false;
    }

    /**
     * This method will be called when certainly time just for item onChanged, not onClicked
     *
     * @param preference
     * @param obj
     * @return
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference.getKey() != null) {
            String key = preference.getKey();
//            if (key.equals(getString(R.string.key_night_mode))) {
////                LogUtil.d(obj);
//                PrefUtil.setIsNightMode(CastUtil.cast2boolean(obj));
//                startMyself();
//            } else
            if (key.equals(getString(R.string.key_always_anon))) {
                PrefUtil.setIsAlwaysAnonymous(CastUtil.cast2boolean(obj));
            } else if (key.equals(getString(R.string.key_simple_board_list))) {
                PrefUtil.setIsSimpleBoardList(CastUtil.cast2boolean(obj));
            } else if (key.equals(getString(R.string.key_simple_forum))) {
                HandlerUtil.postDelay(() -> ActivityManager.getActivityManager().recreateAllActivity(SettingsActivity.class), 10);
            } else if (key.equals(getString(R.string.key_disabled_image_cache))) {
                PrefUtil.setDisabledImageCache(CastUtil.cast2boolean(obj));
            } else if (key.equals(getString(R.string.key_more_app))) {

            }
        }
        return true;
    }

    private void reallyLogout() {
        DialogUtil.alertDialog(mActivity, "真的要登出当前的账户吗？？", "真的", "假的",
                ((materialDialog, dialogAction) -> logout()), null);
    }

    public void startMyself() {
        HandlerUtil.postDelay(() -> ActivityManager.getActivityManager().recreateAllActivity(SettingsActivity.class), 100);
        ActivityManager.getActivityManager().finishActivity(mActivity);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        Intent intent = mActivity.getIntent();
        intent.putExtra(IS_SWITCH_NIGHT_MODE_LOCK, true);
        startActivity(intent);
    }

    public void logout() {
        AuthTool.logout(mActivity);
    }

    private void checkUpdate() {
        UpdateTool.checkUpdate();
    }

}
