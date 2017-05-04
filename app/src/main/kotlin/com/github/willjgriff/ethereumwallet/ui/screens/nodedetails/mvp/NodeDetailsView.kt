package com.github.willjgriff.ethereumwallet.ui.screens.nodedetails.mvp

/**
 * Created by Will on 20/03/2017.
 */
interface NodeDetailsView {

    fun newHeaderHash(headerHash: String)

    fun updateNumberOfPeers(numberOfPeers: Long)

    fun setNodeDetails(nodeInfoString: String)

    fun setSyncProgress(syncProgressString: String)

    fun setPeerStrings(peers: List<String>)
}