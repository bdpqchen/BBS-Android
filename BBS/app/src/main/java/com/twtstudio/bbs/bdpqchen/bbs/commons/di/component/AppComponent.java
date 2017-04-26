package com.twtstudio.bbs.bdpqchen.bbs.commons.di.component;


import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.module.AppModule;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.ContextLife;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerApp;

import dagger.Component;

/**
 * Created by bdpqchen on 17-4-26.
 */

@PerApp
@Component(modules = AppModule.class)
public interface AppComponent {

    // TODO: 17-4-26 getApplication() is never used
    @ContextLife()
    App getApplication();


}
