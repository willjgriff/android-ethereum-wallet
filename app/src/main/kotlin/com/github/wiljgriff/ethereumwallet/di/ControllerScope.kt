package com.github.wiljgriff.ethereumwallet.di

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Created by williamgriffiths on 17/04/2017.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ControllerScope