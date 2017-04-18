package com.github.willjgriff.ethereumwallet.ethereum

import android.content.Context
import android.preference.PreferenceManager
import com.github.willjgriff.ethereumwallet.ethereum.account.ActiveAccountAddress
import com.github.willjgriff.ethereumwallet.ethereum.account.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.account.balance.AccountBalance
import com.github.willjgriff.ethereumwallet.ethereum.adapters.GethAdapterFactory
import com.github.willjgriff.ethereumwallet.ethereum.node.DomainNode
import com.github.willjgriff.ethereumwallet.ethereum.node.NodeDetails

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
    private val activeAccountAddress = ActiveAccountAddress(PreferenceManager.getDefaultSharedPreferences(context))

    val nodeDetails = NodeDetails(gethAdapterFactory.nodeDetailsAdapter)
    val walletAccountManager = AddressManager(gethAdapterFactory.accountsAdapter, activeAccountAddress)
    val accountBalance = AccountBalance(gethAdapterFactory.accountBalanceAdapter, walletAccountManager)
}