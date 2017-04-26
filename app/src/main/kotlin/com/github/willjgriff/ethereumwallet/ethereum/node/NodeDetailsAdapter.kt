package com.github.willjgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Node

/**
 * Created by Will on 16/03/2017.
 *
 * This is a likely candidate for being replaced in the future, eg for Parity or similar.
 * This should not return objects from the Ethereum library but just domain objects.
 */
class NodeDetailsAdapter(private val node: Node,
                         private val ethereumClient: EthereumClient,
                         private val context: Context) {

    fun getNodeInfo(): String = node.getNodeInfoString()

    fun getNumberOfPeers(): Long = node.peersInfo.size()

    fun getPeersInfo(): List<String> = node.getPeersInfoStrings()

    fun getSyncProgressString(): String = ethereumClient.getSyncProgressString(context)
}