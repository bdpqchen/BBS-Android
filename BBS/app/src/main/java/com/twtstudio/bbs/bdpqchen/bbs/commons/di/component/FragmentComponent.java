package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.FragmentModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerFragment;
import com.twtstudio.bbs.bdpqchen.bbs.forum.ForumFragment;
import com.twtstudio.bbs.bdpqchen.bbs.individual.IndividualFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.MainFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.TopTen.TopTenFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.historyHot.HistoryHotFragment;
import com.twtstudio.bbs.bdpqchen.bbs.main.latestPost.LatestPostFragment;
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
    void inject(LatestPostFragment latestPostFragment);
    void inject(TopTenFragment topTenFragment);
    void inject(HistoryHotFragment historyHotFragment);
}
