package com.github.willjgriff.ethereumwallet.ui.screens.transactions.mvp

import com.github.willjgriff.ethereumwallet.ethereum.transactions.TransactionsManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsPresenter @Inject constructor(private val transactionsManager: TransactionsManager) : BaseMvpPresenterKotlin<TransactionsView>() {

    private lateinit var transactionsDisposable: Disposable

    fun setObservables(clearTransactions: Observable<Any>, selectSearchRange: Observable<Any>) {
        setupClearTransactions(clearTransactions)
        setupSelectSearchRange(selectSearchRange)
    }

    private fun setupSelectSearchRange(selectSearchRange: Observable<Any>) {
        addDisposable(selectSearchRange.subscribe { view?.displayRangeDialog() })
    }

    override fun viewReady() {
        subscribeToTransactions()
    }

    private fun subscribeToTransactions() {
        transactionsDisposable = transactionsManager
                .transactionsObservable.subscribe { view?.addTransaction(it) }
        addDisposable(transactionsDisposable)
    }

    private fun setupClearTransactions(clearTransactions: Observable<Any>) {
        clearTransactions.subscribe {
            view?.clearTransactions()
            transactionsManager.clearAndRestart()
            transactionsDisposable.dispose()
            subscribeToTransactions()
        }
    }
}