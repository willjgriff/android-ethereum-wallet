package com.github.wiljgriff.ethereumwallet.ethereum.node

import org.ethereum.geth.*

/**
 * Created by Will on 16/03/2017.
 *
 * This is a likely candidate for being replaced in the future, eg for Parity or similar.
 */
class EthereumDelegate(ethereumFilePath: String) {

    // TODO: Find out what this number means. It may be MB of cache for lightchaindata.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L
    private val DEFAULT_TO_CURRENT_BLOCK_VALUE = -1L

    private val node = Node(ethereumFilePath, NodeConfig())
    private val ethereumClient: EthereumClient by lazy { node.ethereumClient }
    private val context = Context()

    init {
        node.start()
    }

    fun subscribeNewHeaderHandler(newHeadHandler: NewHeadHandler): Subscription =
            ethereumClient.subscribeNewHead(context, newHeadHandler, SOME_RANDOM_SAMPLING_SIZE)

    fun getNodeInfoString() = node.getNodeInfoString()

    fun getBalanceAt(address: Address): BigInt = ethereumClient.getBalanceAt(context, address, DEFAULT_TO_CURRENT_BLOCK_VALUE)

    fun getPendingBalanceAt(address: Address) = ethereumClient.getPendingBalanceAt(context, address)

    fun getPeersInfo() = node.peersInfo

    fun getPeersInfoStrings() = node.getPeersInfoStrings()

    fun getSyncProgressString() = ethereumClient.getSyncProgressString(context)

    fun potentiallyUsefulMethods() {

//        ethereumClient.sendTransaction()
//
//        ethereumClient.callContract()
//
//        ethereumClient.getPendingBalanceAt()
//
//        ethereumClient.getPendingTransactionCount()
//
//        ethereumClient.estimateGas()
    }

}