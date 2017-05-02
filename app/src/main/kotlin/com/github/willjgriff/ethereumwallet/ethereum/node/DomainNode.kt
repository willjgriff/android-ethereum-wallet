package com.github.willjgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.Geth
import org.ethereum.geth.Node
import org.ethereum.geth.NodeConfig

/**
 * Created by williamgriffiths on 18/04/2017.
 */
class DomainNode(nodeFilePath: String) {

    val node: Node

    init {
        val nodeConfig = NodeConfig()

        //EthereumNetworkId: 1 = MainNet, 2 = Morden, 3 = Ropsten, 4 = Rinkeby (Note: Kovan requires the Parity client)
        nodeConfig.ethereumNetworkID = 1
        nodeConfig.ethereumDatabaseCache = 16

        node = Geth.newNode(nodeFilePath, nodeConfig)
        node.start()
    }
}