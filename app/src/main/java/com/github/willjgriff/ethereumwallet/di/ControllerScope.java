package com.github.willjgriff.ethereumwallet.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Will on 29/01/2017.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerScope {
}

