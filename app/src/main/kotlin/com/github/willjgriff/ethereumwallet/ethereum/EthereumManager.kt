package com.github.willjgriff.ethereumwallet.ethereum

import android.content.Context
import android.preference.PreferenceManager
import com.github.willjgriff.ethereumwallet.ethereum.adapters.GethAdapterFactory
import com.github.willjgriff.ethereumwallet.ethereum.address.ActiveAddress
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AccountBalance
import com.github.willjgriff.ethereumwallet.ethereum.node.DomainNode
import com.github.willjgriff.ethereumwallet.ethereum.node.NodeDetails
import com.github.willjgriff.ethereumwallet.ethereum.transaction.TransactionManager

/**
 * Created by williamgriffiths on 17/04/2017.
 *
 * This class should not include any objects from the chosen Ethereum implementation, currently Geth.
 * Those objects should be configured and injected into Adapters in the [GethAdapterFactory].
 * The objects the [EthereumManager] returns should only use these adapters and no Ethereum related objects.
 */
class EthereumManager(context: Context) {

    private val nodeFilePath = context.filesDir.toString() + "/ethereum_node/"
    private val keyStoreFilePath = context.filesDir.toString() + "/ethereum_key_store/"

    private val domainNode = DomainNode(nodeFilePath)
    private val gethAdapterFactory = GethAdapterFactory(domainNode, keyStoreFilePath)
    private val activeAccountAddress = ActiveAddress(PreferenceManager.getDefaultSharedPreferences(context))

    val nodeDetails = NodeDetails(gethAdapterFactory.nodeDetailsAdapter)
    val addressManager = AddressManager(gethAdapterFactory.accountsAdapter, activeAccountAddress)
    val accountBalance = AccountBalance(gethAdapterFactory.accountBalanceAdapter, addressManager)
    val transactionManager = TransactionManager(addressManager, gethAdapterFactory.transactionAdapter)
}