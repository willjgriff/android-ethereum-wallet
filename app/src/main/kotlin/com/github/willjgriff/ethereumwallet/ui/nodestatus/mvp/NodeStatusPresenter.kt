package com.github.willjgriff.ethereumwallet.ui.nodestatus.mvp

import com.github.willjgriff.ethereumwallet.ethereum.node.NodeDetails
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusPresenter @Inject constructor(private val nodeDetails: NodeDetails) : BaseMvpPresenterKotlin<NodeStatusView>() {

    override fun viewReady() {
        addDisposable(nodeDetails.cachedBlockHeaderObservable
                .subscribe({ (hashHex, _) -> view?.newHeaderHash(hashHex) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(nodeDetails.getNumberOfPeers()
                .subscribe({ view?.updateNumberOfPeers(it) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(nodeDetails.getSyncProgressString()
                .subscribe({ view?.setSyncProgress(it) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(nodeDetails.getNodePeerInfoStrings()
                .subscribe({ view?.setPeerStrings(it) },
                        { throwable -> Timber.e(throwable) }))

        view?.setNodeDetails(nodeDetails.getNodeInfoString())
    }
}