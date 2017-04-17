package com.github.wiljgriff.ethereumwallet.ethereum.node

import com.github.wiljgriff.ethereumwallet.data.model.DomainHeader
import org.ethereum.geth.*
import java.math.BigInteger

/**
 * Created by Will on 16/03/2017.
 *
 * This is a likely candidate for being replaced in the future, eg for Parity or similar.
 * This should not return objects from the Ethereum library but just domain objects.
 */
class NodeBridge(ethereumFilePath: String) {

    // TODO: Find out what this number means. It may be MB of cache for lightchaindata.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L
    private val DEFAULT_TO_CURRENT_BLOCK_VALUE = -1L

    private val node = Node(ethereumFilePath, NodeConfig())
    private val ethereumClient: EthereumClient by lazy { node.ethereumClient }
    private val context = Context()

    init {
        node.start()
    }

    fun subscribeNewHeaderHandler(newDomainHeaderListener: NodeDetails.NewDomainHeaderListener): Subscription =
            ethereumClient
                    .subscribeNewHead(context, object : NewHeadHandler {
                        override fun onNewHead(header: Header) {
                            newDomainHeaderListener.onNewDomainHeader(DomainHeader.fromHeader(header))
                        }

                        override fun onError(error: String) {
                            newDomainHeaderListener.onError(error)
                        }

                    }, SOME_RANDOM_SAMPLING_SIZE)

    fun getNodeInfoString(): String = node.getNodeInfoString()

    fun getBalanceAt(addressString: String): BigInteger =
            BigInteger(ethereumClient.getBalanceAt(context, Address(addressString), DEFAULT_TO_CURRENT_BLOCK_VALUE).string())

    fun getPendingBalanceAt(addressString: String): BigInteger =
            BigInteger(ethereumClient.getPendingBalanceAt(context, Address(addressString)).string())

    fun getPeersInfoSize(): Long = node.peersInfo.size()

    fun getPeersInfoStrings(): List<String> = node.getPeersInfoStrings()

    fun getSyncProgressString(): String = ethereumClient.getSyncProgressString(context)

    fun getSuggestedGasPrice() = BigInteger(ethereumClient.suggestGasPrice(context).string())

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