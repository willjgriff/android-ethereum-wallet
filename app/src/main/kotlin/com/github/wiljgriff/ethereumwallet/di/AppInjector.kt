package com.github.wiljgriff.ethereumwallet.di

import android.content.Context
import com.github.wiljgriff.ethereumwallet.di.modules.AppModule

/**
 * Created by williamgriffiths on 17/04/2017.
 */
object AppInjector {

    lateinit var appComponent: AppComponent
        set

    fun init(context: Context) {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(context))
                .build()
    }

}