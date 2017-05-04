package com.github.willjgriff.ethereumwallet.ui.screens.createaccount.di

import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import com.github.willjgriff.ethereumwallet.ui.screens.createaccount.CreateAccountController
import dagger.Component

/**
 * Created by williamgriffiths on 03/05/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface CreateAccountComponent {

    fun inject(createAccountController: CreateAccountController)
}

fun CreateAccountController.injectPresenter() {
    DaggerCreateAccountComponent
            .builder()
            .appComponent(AppInjector.appComponent)
            .build()
            .inject(this)
}