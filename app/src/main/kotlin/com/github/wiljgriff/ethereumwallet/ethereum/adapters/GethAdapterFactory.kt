package com.github.wiljgriff.ethereumwallet.ethereum.adapters

import com.github.wiljgriff.ethereumwallet.ethereum.account.balance.AccountBalanceAdapter
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsAdapter
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetailsAdapter
import com.github.wiljgriff.ethereumwallet.ethereum.transaction.TransactionAdapter
import org.ethereum.geth.*

/**
 * Created by williamgriffiths on 17/04/2017.
 *
 * This class should contain as few non-Geth related objects as possible and return Adapters
 * that abstract the Geth related classes away from the rest of the app.
 */
class GethAdapterFactory(nodeFilePath: String,
                         keyStoreFilePath: String) {

    private val node = Node(nodeFilePath, NodeConfig())
    private val ethClient = node.ethereumClient
    private val context = Context()
    private val keyStore = KeyStore(keyStoreFilePath, Geth.LightScryptN, Geth.LightScryptP)

    val nodeDetailsAdapter = NodeDetailsAdapter(node, ethClient, context)
    val accountsAdapter = AccountsAdapter(keyStore)
    val accountBalanceAdapter = AccountBalanceAdapter(ethClient, context)
    val transactionAdapter = TransactionAdapter(keyStore, ethClient, context)

    fun startSync() {
        node.start()
    }

}