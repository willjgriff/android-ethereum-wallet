package com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp

import org.ethereum.geth.Header
import org.ethereum.geth.PeerInfos

/**
 * Created by Will on 20/03/2017.
 */
interface NodeStatusView {

    fun newHeaderHash(headerHash: String)

    fun updateNumberOfPeers(numberOfPeers: Long)

    fun setNodeDetails(nodeInfoString: String)

    fun setSyncProgress(syncProgressString: String)

    fun setPeerStrings(peers: List<String>)
}