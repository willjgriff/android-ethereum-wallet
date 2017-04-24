package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.SendTransaction
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager

/**
 * Created by williamgriffiths on 16/04/2017.
 */
class TransactionManager(private val addressManager: AddressManager,
                         private val transactionAdapter: TransactionAdapter) {

    fun executeTransaction(sendTransaction: SendTransaction, password: String) {

        transactionAdapter.submitTransaction(sendTransaction, addressManager.getActiveAddress(), password)

        // Store transaction somewhere, or always get them from the network...

    }
}