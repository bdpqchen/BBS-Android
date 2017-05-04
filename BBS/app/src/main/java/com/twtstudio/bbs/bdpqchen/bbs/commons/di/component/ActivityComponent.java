package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.individual.SettingsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.ActivityModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerActivity;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.replaceUser.replacePassword.ReplacePasswordActivity;
import com.twtstudio.bbs.bdpqchen.bbs.replaceUser.ReplaceUserActivity;

import dagger.Component;

/**
 * Created by bdpqchen on 17-4-26.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(HomeActivity homeActivity);

    void inject(RegisterActivity registerActivity);

    void inject(LoginActivity loginActivity);

    void inject(ReplaceUserActivity replaceUserActivity);

    void inject(ReplacePasswordActivity replacePasswordActivity);

    void inject(SettingsActivity settingsActivity);
}
