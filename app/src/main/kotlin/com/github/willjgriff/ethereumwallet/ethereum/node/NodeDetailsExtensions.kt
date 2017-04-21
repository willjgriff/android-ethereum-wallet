package com.github.willjgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Node

/**
 * Created by williamgriffiths on 18/04/2017.
 */
fun EthereumClient.getSyncProgressString(context: Context): String {
    syncProgress(context)?.let {
        return "\nCurrent Block: ${it.currentBlock}\n" +
                "Starting Block: ${it.startingBlock}\n" +
                "Highest Block: ${it.highestBlock}\n" +
                "Known States: ${it.knownStates}\n" +
                "Pulled Stats: ${it.pulledStates}"
    }
    return "\nSYNC PROGRESS NULL"
}

fun Node.getNodeInfoString(): String =
        "Name: ${nodeInfo.name}\n" +
                "Protocols: ${nodeInfo.protocols}\n" +
                "Ip: ${nodeInfo.ip}\n" +
                "Discovery Port: ${nodeInfo.discoveryPort}\n" +
                "Listener Address: ${nodeInfo.listenerAddress}\n" +
                "Listener Port: ${nodeInfo.listenerPort}\n" +
                "Id: ${nodeInfo.id}\n" +
                "Enode: ${nodeInfo.enode}\n"

fun Node.getPeersInfoStrings(): List<String> =
        0L.rangeTo(peersInfo.size() - 1)
                .map { peersInfo.get(it) }
                .map {
                    "Name: ${it.name}\n" +
                            //                        "ID: ${it.id}\n" +
                            "Remote Address: ${it.remoteAddress}\n" +
                            "Local Address:${it.localAddress}\n" +
                            "Caps: ${it.caps}\n"
                }
                .toList()