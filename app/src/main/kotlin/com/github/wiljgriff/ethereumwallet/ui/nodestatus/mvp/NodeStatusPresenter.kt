package com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp

import com.github.wiljgriff.ethereumwallet.ethereum.node.Ethereum
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusPresenter @Inject constructor(private val ethereum: Ethereum) : BaseMvpPresenter<NodeStatusView>() {

    override fun viewReady() {
        addDisposable(ethereum.cachedBlockHeaderObservable
                .subscribe({ header -> view.newHeader(header) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(ethereum.getPeersInfo()
                .subscribe({ peerInfos -> view.updatePeerInfos(peerInfos) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(ethereum.getSyncProgressString()
                .subscribe({ view.setSyncProgress(it) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(ethereum.getNodePeerInfoStrings()
                .subscribe({ view.setPeerStrings(it) },
                        { throwable -> Timber.e(throwable) }))

        view.setNodeDetails(ethereum.getNodeInfoString())
    }
}