package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.ActivityModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.replaceUser.replacePassword.ReplacePasswordActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.replaceUser.ReplaceUserActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword.UpdatePassword;

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

    void inject(UpdateInfoActivity updateInfoActivity);

    void inject(UpdatePassword updatePassword);

    void inject(BoardsActivity boardsActivity);

    void inject(ThreadActivity threadActivity);


//    void inject(SettingsActivity settingsActivity);
}
