package com.github.wiljgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.*

/**
 * Created by Will on 02/04/2017.
 */
fun Node.getNodeInfoString(): String {
    return "Name: ${nodeInfo.name}\n" +
            "Protocols: ${nodeInfo.protocols}\n" +
            "Ip: ${nodeInfo.ip}\n" +
            "Discovery Port: ${nodeInfo.discoveryPort}\n" +
            "Listener Address: ${nodeInfo.listenerAddress}\n" +
            "Listener Port: ${nodeInfo.listenerPort}"
//            "Id: ${nodeInfo.id}\n" +
//            "Enode: ${nodeInfo.enode}\n"
}

fun Node.getPeersInfoStrings(): List<String> {
    return 0L.rangeTo(peersInfo.size() - 1)
            .map { peersInfo.get(it) }
            .map {
                "Name: ${it.name}\n" +
//                        "ID: ${it.id}\n" +
                        "Remote Address: ${it.remoteAddress}\n" +
                        "Local Address:${it.localAddress}\n" +
                        "Caps: ${it.caps}\n"
            }
            .toList()
}

fun EthereumClient.getSyncProgressString(context: Context): String {
    syncProgress(context)?.let {
        return "Current Block: ${it.currentBlock}\n" +
                "Starting Block: ${it.startingBlock}\n" +
                "Highest Block: ${it.highestBlock}\n" +
                "Known States: ${it.knownStates}\n" +
                "Pulled Stats: ${it.pulledStates}"
    }
    return ""
}