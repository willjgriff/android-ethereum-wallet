package com.github.willjgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.Node
import org.ethereum.geth.NodeConfig

/**
 * Created by williamgriffiths on 18/04/2017.
 */
class DomainNode(nodeFilePath: String) {

    val node = Node(nodeFilePath, NodeConfig())

    init {
        node.start()
    }
}