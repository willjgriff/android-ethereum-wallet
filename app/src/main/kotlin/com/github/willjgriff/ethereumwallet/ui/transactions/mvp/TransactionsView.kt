package com.github.willjgriff.ethereumwallet.ui.transactions.mvp

import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction

/**
 * Created by williamgriffiths on 11/04/2017.
 */
interface TransactionsView {

    fun addTransaction(transactions: DomainTransaction)
}