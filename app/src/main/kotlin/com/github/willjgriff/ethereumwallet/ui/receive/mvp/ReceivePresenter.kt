package com.github.willjgriff.ethereumwallet.ui.receive.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AddressBalance
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import javax.inject.Inject

/**
 * Created by williamgriffiths on 18/04/2017.
 */
class ReceivePresenter @Inject constructor(private val addressManager: AddressManager, private val addressBalance: AddressBalance)
    : BaseMvpPresenterKotlin<ReceiveView>() {

    override fun viewReady() {
        view?.setReceiveAddress(addressManager.getActiveAddress().hex)

        addDisposable(addressBalance.getPendingBalanceAtActiveAddress()
                .subscribe { view?.setPendingBalance(it) })

        addDisposable(addressBalance.getBalanceAtActiveAddress()
                .subscribe { view?.setConfirmedBalance(it) })
    }
}