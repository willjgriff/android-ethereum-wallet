package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsManager(private val transactionsAdapter: TransactionsAdapter, private val addressManager: AddressManager) {

    private val TRANSACTION_SEARCH_FROM_BLOCK = 3563300L
    private val TRANSACTION_SEARCH_TO_BLOCK = 3563500L

    fun getTransactionsFromSomeTime(): List<DomainTransaction> {
        val address = addressManager.getActiveAddress()
        return transactionsAdapter.getTransactionsInBlockRange(address, TRANSACTION_SEARCH_FROM_BLOCK, TRANSACTION_SEARCH_TO_BLOCK)
    }
}