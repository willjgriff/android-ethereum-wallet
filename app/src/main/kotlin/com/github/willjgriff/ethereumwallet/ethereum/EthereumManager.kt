package com.github.willjgriff.ethereumwallet.ethereum

import android.content.Context
import android.preference.PreferenceManager
import com.github.willjgriff.ethereumwallet.ethereum.address.ActiveAddress
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AddressBalance
import com.github.willjgriff.ethereumwallet.ethereum.node.DomainNode
import com.github.willjgriff.ethereumwallet.ethereum.node.NodeDetails
import com.github.willjgriff.ethereumwallet.ethereum.transaction.TransactionManager
import com.github.willjgriff.ethereumwallet.ethereum.transactions.TransactionsManager
import com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.SharedPrefsTransactionsStorage
import com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.TransactionsStorage

/**
 * Created by williamgriffiths on 17/04/2017.
 *
 * This class should not include any objects from the chosen Ethereum implementation, currently Geth.
 * Those objects should be configured and injected into Adapters in the [GethAdapterFactory].
 * The objects the [EthereumManager] returns should only use these adapters and no Ethereum related objects.
 */
class EthereumManager(context: Context) {

    private val nodeFilePathMain = context.filesDir.toString() + "/ethereum_node/"
    private val keyStoreFilePath = context.filesDir.toString() + "/ethereum_key_store/"

    private val domainNode = DomainNode(nodeFilePathMain)
    private val gethAdapterFactory = GethAdapterFactory(domainNode, keyStoreFilePath)
    private val activeAccountAddress = ActiveAddress(PreferenceManager.getDefaultSharedPreferences(context))

    /** This can be switched for anything that implements [TransactionsStorage] */
    var transactionsStorage = SharedPrefsTransactionsStorage(context)

    val nodeDetails = NodeDetails(gethAdapterFactory.nodeDetailsAdapter, gethAdapterFactory.newBlockHeaderAdapter)
    val addressManager = AddressManager(gethAdapterFactory.accountsAdapter, activeAccountAddress)
    val accountBalance = AddressBalance(gethAdapterFactory.accountBalanceAdapter, addressManager)
    val transactionManager = TransactionManager(addressManager, gethAdapterFactory.transactionAdapter)
    val transactionsManager = TransactionsManager(transactionsStorage, gethAdapterFactory.transactionsAdapter, gethAdapterFactory.newBlockHeaderAdapter, addressManager)
}