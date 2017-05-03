package com.github.willjgriff.ethereumwallet.ui.send.di

import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import com.github.willjgriff.ethereumwallet.ui.send.SendController
import dagger.Component

/**
 * Created by williamgriffiths on 03/05/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface SendComponent {

    fun inject(sendController: SendController)
}

fun SendController.injectPresenter() {
    DaggerSendComponent.builder()
            .appComponent(AppInjector.appComponent)
            .build()
            .inject(this)
}