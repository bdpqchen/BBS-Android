package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.old.RegisterOldActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.appeal.AppealPassportActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve.RetrieveActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.ActivityModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.BoardsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.create_thread.CreateThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListActivity;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.friend.FriendActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.letter.LetterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.message.MessageActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.MyReleaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.star.StarActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo.UpdateInfoActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword.UpdatePasswordActivity;
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleActivity;

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

    void inject(UpdateInfoActivity updateInfoActivity);

    void inject(UpdatePasswordActivity updatePasswordActivity);

    void inject(BoardsActivity boardsActivity);

    void inject(ThreadActivity threadActivity);

    void inject(MessageActivity messageActivity);

    void inject(MyReleaseActivity myReleaseActivity);

    void inject(ThreadListActivity threadListActivity);

    void inject(AppealPassportActivity appealPassportActivity);

    void inject(IdentifyActivity identifyActivity);

    void inject(RetrieveActivity retrieveActivity);

    void inject(CreateThreadActivity createThreadActivity);

    void inject(RegisterOldActivity registerOldActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(StarActivity starActivity);

    void inject(FriendActivity friendActivity);

    void inject(PeopleActivity peopleActivity);

    void inject(LetterActivity letterActivity);
}
