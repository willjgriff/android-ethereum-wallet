package com.github.wiljgriff.ethereumwallet.ethereum

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsAdapter
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeAdapter
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import org.ethereum.geth.Node
import org.ethereum.geth.NodeConfig

/**
 * Created by williamgriffiths on 17/04/2017.
 *
 * This class should contain as few non-Geth related objects as possible
 */
class GethAdapterFactory(nodeFilePath: String, keyStoreFilePath: String) {

    val node = Node(nodeFilePath, NodeConfig())
    val nodeAdapter = NodeAdapter(node)

    val keyStore = KeyStore(keyStoreFilePath, Geth.LightScryptN, Geth.LightScryptP)
    val accountsAdapter = AccountsAdapter(keyStore)

    fun startSync() {
        node.start()
    }

}