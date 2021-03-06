package com.github.willjgriff.ethereumwallet.ui.screens.receive.mvp

/**
 * Created by williamgriffiths on 18/04/2017.
 */
interface ReceiveView {

    fun setReceiveAddress(address: String)
    fun setPendingBalance(pendingBalance: String)
    fun setConfirmedBalance(confirmedBalance: String)

}