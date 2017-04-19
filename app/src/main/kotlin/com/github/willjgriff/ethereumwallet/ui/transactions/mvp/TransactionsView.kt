package com.github.willjgriff.ethereumwallet.ui.transactions.mvp

import com.github.willjgriff.ethereumwallet.data.model.DomainTransaction

/**
 * Created by williamgriffiths on 11/04/2017.
 */
interface TransactionsView {

    fun setTransactions(transactions: List<DomainTransaction>)
}