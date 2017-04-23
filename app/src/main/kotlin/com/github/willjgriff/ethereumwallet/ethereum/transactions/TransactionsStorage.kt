package com.github.willjgriff.ethereumwallet.ethereum.transactions

import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction

/**
 * Created by williamgriffiths on 21/04/2017.
 */
class TransactionsStorage {

    private val transactions: MutableList<DomainTransaction> = mutableListOf()

    fun getStoredTransactions(): List<DomainTransaction> {
        return transactions
    }

    fun storeTransaction(transaction: DomainTransaction) {
        transactions.add(transaction)
    }

}