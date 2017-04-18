package com.github.willjgriff.ethereumwallet.ethereum.node

import com.github.willjgriff.ethereumwallet.ethereum.node.getNodeInfoString
import com.github.willjgriff.ethereumwallet.ethereum.node.getPeersInfoStrings
import com.github.willjgriff.ethereumwallet.ethereum.node.getSyncProgressString
import com.github.willjgriff.ethereumwallet.data.model.DomainHeader
import com.github.willjgriff.ethereumwallet.ethereum.node.NodeDetails
import org.ethereum.geth.*

/**
 * Created by Will on 16/03/2017.
 *
 * This is a likely candidate for being replaced in the future, eg for Parity or similar.
 * This should not return objects from the Ethereum library but just domain objects.
 */
class NodeDetailsAdapter(private val node: Node,
                         private val ethereumClient: EthereumClient,
                         private val context: Context) {

    // TODO: Find out what this number means. It may be MB of cache for lightchaindata.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L

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

    fun getNodeInfo(): String = node.getNodeInfoString()

    fun getNumberOfPeers(): Long = node.peersInfo.size()

    fun getPeersInfo(): List<String> = node.getPeersInfoStrings()

    fun getSyncProgressString(): String = ethereumClient.getSyncProgressString(context)
}