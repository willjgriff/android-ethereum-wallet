package com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp

import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumNode
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusPresenter @Inject constructor(private val ethereumNode: EthereumNode) : BaseMvpPresenter<NodeStatusView>() {

    override fun viewReady() {
        addDisposable(ethereumNode.cachedBlockHeaderObservable
                .subscribe({ header -> view.newHeader(header) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(ethereumNode.getPeersInfo()
                .subscribe({ peerInfos -> view.updatePeerInfos(peerInfos) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(ethereumNode.getSyncProgressString()
                .subscribe({ view.setSyncProgress(it) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(ethereumNode.getNodePeerInfoStrings()
                .subscribe({ view.setPeerStrings(it) },
                        { throwable -> Timber.e(throwable) }))

        view.setNodeDetails(ethereumNode.getNodeInfoString())
    }
}