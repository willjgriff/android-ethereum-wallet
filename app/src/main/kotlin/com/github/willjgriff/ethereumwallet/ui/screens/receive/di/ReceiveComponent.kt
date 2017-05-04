package com.github.willjgriff.ethereumwallet.ui.screens.receive.di

import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import com.github.willjgriff.ethereumwallet.ui.screens.receive.ReceiveController
import dagger.Component

/**
 * Created by williamgriffiths on 18/04/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface ReceiveComponent {

    fun inject(receiveController: ReceiveController)
}

fun ReceiveController.injectNewReceivePresenter() {
    DaggerReceiveComponent.builder()
            .appComponent(AppInjector.appComponent)
            .build()
            .inject(this)
}