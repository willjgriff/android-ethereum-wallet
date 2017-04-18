package com.github.willjgriff.ethereumwallet.ui.transactions.mvp

import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsView
import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AccountBalance
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsPresenter @Inject constructor(private val accountBalance: AccountBalance) : BaseMvpPresenter<TransactionsView>() {

    override fun viewReady() {

        addDisposable(accountBalance.getBalanceAtActiveAddress()
                .subscribe { view.setBalance(it) })

        addDisposable(accountBalance.getPendingBalanceAtActiveAddress()
                .subscribe { view.setPendingBalance(it) })
    }
}