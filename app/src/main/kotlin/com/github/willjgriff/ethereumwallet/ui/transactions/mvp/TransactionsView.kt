package com.github.willjgriff.ethereumwallet.ui.transactions.mvp

/**
 * Created by williamgriffiths on 11/04/2017.
 */
interface TransactionsView {

    fun setBalance(balanceAtAddress: String)

    fun setPendingBalance(pendingBalanceAtAddress: String)
}