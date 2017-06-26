package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.FragmentModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerFragment;
import com.twtstudio.bbs.bdpqchen.bbs.forum.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.MyReleaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.my_release.my_reply.MyReplyFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.hot.HotFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestFragment;

import dagger.Component;

/**
 * Created by bdpqchen on 17-5-2.
 */

@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(IndividualFragment individualFragment);
    void inject(ForumFragment forumFragment);

    void inject(MyReleaseFragment myReleaseFragment);
    void inject(MyReplyFragment myReplyFragment);

    void inject(LatestFragment latestFragment);
    void inject(HotFragment hotFragment);

}
