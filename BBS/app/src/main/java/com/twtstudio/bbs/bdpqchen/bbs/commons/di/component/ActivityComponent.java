package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal.AppealPassportActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.RetrieveActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.retrieve.reset_password.ResetPasswordActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.replaceUser.ReplaceUserActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.replaceUser.replacePassword.ReplacePasswordActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.ActivityModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListActivity;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.MessageActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword.UpdatePassword;
import com.twtstudio.bbs.bdpqchen.bbs.main.content.ContentActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.MyReleaseActivity;

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

    void inject(MessageActivity messageActivity);

    void inject(MyReleaseActivity myReleaseActivity);

    void inject(ThreadListActivity threadListActivity);

    void inject(ContentActivity contentActivity);

    void inject(AppealPassportActivity appealPassportActivity);

    void inject(IdentifyActivity identifyActivity);

    void inject(RetrieveActivity retrieveActivity);

    void inject(ResetPasswordActivity resetPasswordActivity);
}
