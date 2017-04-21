package com.github.willjgriff.ethereumwallet.ui.transactions.mvp

import com.github.willjgriff.ethereumwallet.ethereum.transaction.TransactionsManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsPresenter @Inject constructor(private val transactionsManager: TransactionsManager) : BaseMvpPresenter<TransactionsView>() {

    override fun viewReady() {

        addDisposable(transactionsManager
                .transactionsObservable.subscribe { view.addTransaction(it) })

    }
}