package com.twtstudio.bbs.bdpqchen.bbs.commons.di.module;

import com.twtstudio.bbs.bdpqchen.bbs.App;
import com.twtstudio.bbs.bdpqchen.bbs.commons.rx.RxDoHttpClient;

import javax.inject.Singleton;

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
    @Singleton
    App provideApplication(){
        return mApp;
    }


    //已经为HttpClient提供了但里模式，无需在RxDoHttpClient里
    @Provides
    @Singleton
    RxDoHttpClient provideRxDoHttpClient(){
        return new RxDoHttpClient<>();
    }



}
