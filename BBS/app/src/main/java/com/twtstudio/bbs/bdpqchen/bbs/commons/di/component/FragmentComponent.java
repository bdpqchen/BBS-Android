package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.FragmentModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerFragment;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.publish.PublishFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.reply.ReplyFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.hot.HotFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latest.LatestFragment;
import com.twtstudio.bbs.bdpqchen.bbs.mdeditor.EditorFragment;
import com.twtstudio.bbs.bdpqchen.bbs.message.personal.PersonalFragment;
import com.twtstudio.bbs.bdpqchen.bbs.message.system.SystemFragment;

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

    void inject(LatestFragment latestFragment);

    void inject(HotFragment hotFragment);

    void inject(SystemFragment systemFragment);

    void inject(PersonalFragment personalFragment);

    void inject(EditorFragment editorFragment);

    void inject(PublishFragment publishFragment);

    void inject(ReplyFragment replyFragment);
}
