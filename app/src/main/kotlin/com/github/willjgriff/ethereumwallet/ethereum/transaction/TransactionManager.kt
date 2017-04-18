package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.data.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.ethereum.account.ActiveAccountAddress

/**
 * Created by williamgriffiths on 16/04/2017.
 */
class TransactionManager(private val activeAccountAddress: ActiveAccountAddress,
                         private val transactionAdapter: TransactionAdapter) {

    fun executeTransaction(domainTransaction: DomainTransaction) {

        transactionAdapter.submitTransaction(domainTransaction)

        // Store transaction somewhere

    }
}