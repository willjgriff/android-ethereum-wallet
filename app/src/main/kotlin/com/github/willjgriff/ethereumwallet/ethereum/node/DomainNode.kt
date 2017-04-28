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

//        val enodes = Enodes()
//        enodes.append(Enode("enode://aca1776ce314c606f7a7c82ee3ec50e34c4ae629e26988c64ab99adab31b52c6e8743e8f668fb4e9eebd8fe1c37448ede48c37c82207cc9cf9f519e14301c330@86.8.21.56:30303"))
//        enodes.append(Enode("enode://c4194be1f2891065f3a650ac66e5a14ada0bba98fa7a90510fe4de31fc51c199c78a4100c61ff6304ac766cfbe7f265959bc64a0a08d2e8f5e886d1fcb0f04bb@86.8.21.56:60836"))

//        EthereumNetworkId: 1 = MainNet, 2 = Morden, 3 = Ropsten, 4 = Rinkeby (Note: Kovan requires the Parity client)
        nodeConfig.ethereumNetworkID = 1
//        nodeConfig.bootstrapNodes = enodes
        nodeConfig.ethereumDatabaseCache = 16

        node = Geth.newNode(nodeFilePath, nodeConfig)
        node.start()
    }
}