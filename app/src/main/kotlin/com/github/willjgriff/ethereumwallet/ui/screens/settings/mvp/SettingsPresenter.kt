package com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class SettingsPresenter @Inject constructor(private val addressManager: AddressManager)
    : BaseMvpPresenterKotlin<SettingsView>() {

    override fun viewReady() {
        getAndSetActiveAddress()
    }

    fun setObservables(newAccountClick: Observable<Any>, changeAddressClick: Observable<Any>, deleteAddressClick: Observable<Any>) {
        newAccountClick.subscribe { _ -> view?.openCreateAccountScreen() }
        changeAddressClick.subscribe { _ -> view?.openChangeAddressScreen() }
        deleteAddressClick.subscribe { _ -> view?.showDeleteAddressDialog() }
    }

    private fun getAndSetActiveAddress() {
        val address: String
        if (addressManager.getActiveAddress().hex != "NO_ADDRESS_HEX") {
            address = addressManager.getActiveAddress().hex
            view?.setActiveAddress(address)
        } else {
            view?.setAddressDeleted()
        }
    }
}