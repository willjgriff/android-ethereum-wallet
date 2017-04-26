package com.github.willjgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.Geth
import org.ethereum.geth.Node
import org.ethereum.geth.NodeConfig

/**
 * Created by williamgriffiths on 18/04/2017.
 */
class DomainNode(nodeFilePath: String) {

    val nodeConfig = NodeConfig()
    val node: Node = Geth.newNode(nodeFilePath, nodeConfig)

    init {
//        EthereumNetworkId: 1 = MainNet, 2 = Morden TestNet, 3 = Ropsten TestNet. I think?
//        nodeConfig.ethereumNetworkID = 1
        node.start()
    }
}