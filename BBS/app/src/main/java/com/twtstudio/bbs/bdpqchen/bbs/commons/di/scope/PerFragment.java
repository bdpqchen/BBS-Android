package com.twtstudio.bbs.bdpqchen.bbs.commons.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by bdpqchen on 17-5-2.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {}
