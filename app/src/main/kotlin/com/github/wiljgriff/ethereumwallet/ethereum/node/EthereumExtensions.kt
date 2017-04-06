package com.github.wiljgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Node
import org.ethereum.geth.PeerInfo

/**
 * Created by Will on 02/04/2017.
 */
fun Node.getNodeInfoString(): String {
    return "Name: ${nodeInfo.name}\n" +
            "Protocols: ${nodeInfo.protocols}\n" +
            "Ip: ${nodeInfo.ip}\n" +
            "Discovery Port: ${nodeInfo.discoveryPort}\n" +
            "Listener Address: ${nodeInfo.listenerAddress}\n" +
            "Listener Port: ${nodeInfo.listenerPort}" +
            "Id: ${nodeInfo.id}\n" +
            "Enode: ${nodeInfo.enode}\n"
}

fun Node.getPeersInfoString(): String {
    return 0L.rangeTo(peersInfo.size() - 1 )
            .map { peersInfo.get(it) }
            .map { "Name: ${it.name} " +
                    "ID: ${it.id} " +
                    "Remote Address: ${it.remoteAddress} " +
                    "Local Address:${it.localAddress} " +
                    "Caps: ${it.caps}\n"}
            .joinToString { it }
}

fun EthereumClient.getSyncProgressString(context: Context): String {
    val syncProgress = syncProgress(context)
    return "Current Block: ${syncProgress.currentBlock}\n" +
            "Starting Block: ${syncProgress.startingBlock}\n" +
            "Highest Block: ${syncProgress.highestBlock}\n" +
            "Known States: ${syncProgress.knownStates}\n" +
            "Pulled Stats: ${syncProgress.pulledStates}"
}