package com.github.willjgriff.ethereumwallet.ui.screens.send.mvp

/**
 * Created by williamgriffiths on 03/05/2017.
 */
interface SendView {
    fun displayTransactionSubmitted()
    fun displayErrorSubmittingTx()
    fun setBalance(balance: String)
    fun setPendingBalance(pendingBalance: String)
}