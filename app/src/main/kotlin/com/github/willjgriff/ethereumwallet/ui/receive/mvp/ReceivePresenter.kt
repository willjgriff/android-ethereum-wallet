package com.github.willjgriff.ethereumwallet.ui.receive.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import javax.inject.Inject

/**
 * Created by williamgriffiths on 18/04/2017.
 */
class ReceivePresenter @Inject constructor(private val addressManager: AddressManager)
    : BaseMvpPresenter<ReceiveView>() {

    override fun viewReady() {
        view.setReceiveAddress(addressManager.getActiveAddress().hex)
    }
}