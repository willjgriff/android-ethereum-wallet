package com.github.wiljgriff.ethereumwallet.ui.transactions.mvp

import com.github.wiljgriff.ethereumwallet.ethereum.node.Ethereum
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsPresenter @Inject constructor(private val ethereum: Ethereum) : BaseMvpPresenter<TransactionsView>() {

    override fun viewReady() {
        addDisposable(ethereum.cachedBalanceAtAddress.subscribe { view.setBalance(it) })

        addDisposable(ethereum.getPendingBalanceAtAddress().subscribe { view.setPendingBalance(it) })
    }
}