package com.github.willjgriff.ethereumwallet.di

import android.content.Context
import com.github.wiljgriff.ethereumwallet.di.DaggerAppComponent
import com.github.willjgriff.ethereumwallet.di.modules.AppModule

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