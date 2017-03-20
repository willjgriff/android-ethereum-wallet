package com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp

import com.github.wiljgriff.ethereumwallet.ethereum.node.Ethereum
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import org.ethereum.geth.Header
import timber.log.Timber

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusPresenter(val ethereum: Ethereum) : BaseMvpPresenter<NodeStatusView>() {

    override fun viewReady() {
        addDisposable(ethereum.getBlockHeaderObservable()
                .subscribe({ header -> view.newHeader(header) },
                        { throwable -> Timber.e(throwable) }))

        addDisposable(ethereum.getPeersInfo()
                .subscribe({ peerInfos -> view.updatePeerInfos(peerInfos) },
                        { throwable -> Timber.e(throwable) }))
    }
}