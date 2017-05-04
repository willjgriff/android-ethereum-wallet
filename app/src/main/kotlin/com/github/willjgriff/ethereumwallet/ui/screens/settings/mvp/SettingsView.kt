package com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp

/**
 * Created by williamgriffiths on 04/05/2017.
 */
interface SettingsView {

    fun openCreateAccountScreen()

    fun showDeleteAddressDialog()

    fun setActiveAddress(address: String)

    fun setAddressDeleted()

    fun openChangeAddressScreen()
}