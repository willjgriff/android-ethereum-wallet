package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.data.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager

/**
 * Created by williamgriffiths on 16/04/2017.
 */
class TransactionManager(private val addressManager: AddressManager,
                         private val transactionAdapter: TransactionAdapter) {

    fun executeTransaction(domainTransaction: DomainTransaction, password: String) {

        transactionAdapter.submitTransaction(domainTransaction, addressManager.getActiveAddress(), password)

        // Store transaction somewhere

    }
}