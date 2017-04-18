package com.github.willjgriff.ethereumwallet.ethereum.adapters

import com.github.willjgriff.ethereumwallet.ethereum.node.DomainNode
import com.github.willjgriff.ethereumwallet.ethereum.node.NodeDetailsAdapter
import com.github.willjgriff.ethereumwallet.ethereum.transaction.TransactionAdapter
import com.github.willjgriff.ethereumwallet.ethereum.account.AddressAdapter
import com.github.willjgriff.ethereumwallet.ethereum.account.balance.AccountBalanceAdapter
import org.ethereum.geth.Context
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore

/**
 * Created by williamgriffiths on 17/04/2017.
 *
 * This class should contain as few non-Geth related objects as possible and return Adapters
 * that abstract the Geth related classes away from the rest of the app.
 */
class GethAdapterFactory(domainNode: DomainNode, keyStoreFilePath: String) {

    private val node = domainNode.node
    private val ethClient = node.ethereumClient
    private val context = Context()
    private val keyStore = KeyStore(keyStoreFilePath, Geth.LightScryptN, Geth.LightScryptP)

    val nodeDetailsAdapter = NodeDetailsAdapter(node, ethClient, context)
    val accountsAdapter = AddressAdapter(keyStore)
    val accountBalanceAdapter = AccountBalanceAdapter(ethClient, context)
    val transactionAdapter = TransactionAdapter(keyStore, ethClient, context)
}