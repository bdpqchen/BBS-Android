package com.twtstudio.bbs.bdpqchen.bbs.commons.di.module;

import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.ContextLife;
import com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bdpqchen on 17-4-26.
 */

@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Provides
    @PerApp
    @ContextLife()
    App provideApplication(){
        return mApp;
    }

}
