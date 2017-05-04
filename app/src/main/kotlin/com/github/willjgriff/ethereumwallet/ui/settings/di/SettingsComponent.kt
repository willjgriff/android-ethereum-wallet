package com.github.willjgriff.ethereumwallet.ui.settings.di

import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import com.github.willjgriff.ethereumwallet.ui.settings.ChangeAddressController
import com.github.willjgriff.ethereumwallet.ui.settings.DeleteAddressAlertDialog
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsController
import dagger.Component

/**
 * Created by williamgriffiths on 04/05/2017.
 */
@ControllerScope
@Component(dependencies = arrayOf(AppComponent::class))
interface SettingsComponent {

    fun inject(settingsController: SettingsController)

    fun inject(alertDialog: DeleteAddressAlertDialog)

    fun inject(changeAddressController: ChangeAddressController)
}

fun SettingsController.injectPresenter() {
    getComponent().inject(this)
}

fun DeleteAddressAlertDialog.injectPresenter() {
    getComponent().inject(this)
}

fun ChangeAddressController.injectPresenter() {
    getComponent().inject(this)
}

private fun getComponent(): SettingsComponent {
    return DaggerSettingsComponent.builder()
            .appComponent(AppInjector.appComponent)
            .build()
}