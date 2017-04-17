package com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp

import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetails
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusPresenter @Inject constructor(private val nodeDetails: NodeDetails) : BaseMvpPresenter<NodeStatusView>() {

    override fun viewReady() {
        addDisposable(nodeDetails.cachedBlockHeaderObservable
                .subscribe({ header -> view.newHeaderHash(header.hashHex) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(nodeDetails.getNumberOfPeers()
                .subscribe({ peerInfos -> view.updateNumberOfPeers(peerInfos) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(nodeDetails.getSyncProgressString()
                .subscribe({ view.setSyncProgress(it) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(nodeDetails.getNodePeerInfoStrings()
                .subscribe({ view.setPeerStrings(it) },
                        { throwable -> Timber.e(throwable) }))

        view.setNodeDetails(nodeDetails.getNodeInfoString())
    }
}