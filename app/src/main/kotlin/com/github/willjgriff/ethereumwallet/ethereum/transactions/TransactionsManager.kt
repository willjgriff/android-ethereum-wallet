package com.github.willjgriff.ethereumwallet.ethereum.transactions

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.extensions.androidIoSchedule
import io.reactivex.Observable

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsManager(private val transactionsStorage: TransactionsStorage,
                          private val transactionsAdapter: TransactionsAdapter,
                          private val addressManager: AddressManager) {

    private val TRANSACTION_SEARCH_FROM_BLOCK = 3563500L
    private val NUMBER_OF_BLOCKS_TO_SEARCH = 200L
    val transactionsObservable: Observable<DomainTransaction> by lazy {
        getTransactionsFromSomeTime()
    }

    // This should continue until either the transaction block to search to has been reached or the page max has been reached.
    private fun getTransactionsFromSomeTime(): Observable<DomainTransaction> {
        val address = addressManager.getActiveAddress()
        return transactionsAdapter
                .getTransactionsInBlockRange(address, TRANSACTION_SEARCH_FROM_BLOCK, NUMBER_OF_BLOCKS_TO_SEARCH)
                .androidIoSchedule()
                .replay()
                .autoConnect()
    }
}