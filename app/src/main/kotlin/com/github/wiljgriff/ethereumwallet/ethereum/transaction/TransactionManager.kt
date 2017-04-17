package com.github.wiljgriff.ethereumwallet.ethereum.transaction

import com.github.wiljgriff.ethereumwallet.data.ethereum.DomainTransaction
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsAdapter
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetailsAdapter

/**
 * Created by williamgriffiths on 16/04/2017.
 */
class TransactionManager(val t: NodeDetailsAdapter, val accountsAdapter: AccountsAdapter) {

    fun executeTransaction(domainTransaction: DomainTransaction) {

    }
}