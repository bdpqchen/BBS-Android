package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;

import android.app.Activity;

import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.ActivityModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerActivity;
import com.twtstudio.bbs.bdpqchen.bbs.home.MainActivity;

import dagger.Component;

/**
 * Created by bdpqchen on 17-4-26.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);


}
