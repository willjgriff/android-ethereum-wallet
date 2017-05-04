package com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import javax.inject.Inject

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class ChangeAddressPresenter @Inject constructor(private val addressManager: AddressManager)
    : BaseMvpPresenterKotlin<ChangeAddressView>() {

    override fun viewReady() {
        view?.setAddresses(addressManager.getAllAddresses())
    }

    fun onAddressItemClicked(domainAddress: DomainAddress) {
        addressManager.setActiveAccount(domainAddress)
        view?.closeScreen()
    }
}
