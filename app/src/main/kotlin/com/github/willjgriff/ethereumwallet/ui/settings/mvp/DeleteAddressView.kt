package com.github.willjgriff.ethereumwallet.ui.settings.mvp

/**
 * Created by williamgriffiths on 04/05/2017.
 */
interface DeleteAddressView {

    fun closeDialog()

    fun incorrectPasswordEntered()

    fun addressDeleted()
}