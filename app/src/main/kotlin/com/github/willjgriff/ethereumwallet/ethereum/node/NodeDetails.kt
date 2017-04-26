package com.github.willjgriff.ethereumwallet.ethereum.node

import com.github.willjgriff.ethereumwallet.ethereum.node.model.DomainBlockHeader
import com.github.willjgriff.ethereumwallet.extensions.androidIoSchedule
import com.github.willjgriff.ethereumwallet.extensions.replayConnect
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class NodeDetails(private val nodeDetailsAdapter: NodeDetailsAdapter,
                  private val newBlockHeaderAdapter: NewBlockHeaderAdapter) {

    private val EMISSION_INTERVAL_SECONDS = 1L
    val cachedBlockHeaderObservable: Observable<DomainBlockHeader> by lazy { getBlockHeaderObservable() }

    private fun getBlockHeaderObservable(): Observable<DomainBlockHeader> {
        return newBlockHeaderAdapter
                .newBlockHeaderObservable
                .throttleFirst(EMISSION_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .replayConnect(10)
                .androidIoSchedule()
    }

    fun getNodeInfoString() = nodeDetailsAdapter.getNodeInfo()

    fun getNumberOfPeers(): Observable<Long> = getFuncOnIntervalObservable { nodeDetailsAdapter.getNumberOfPeers() }

    fun getNodePeerInfoStrings(): Observable<List<String>> = getFuncOnIntervalObservable { nodeDetailsAdapter.getPeersInfo() }

    fun getSyncProgressString(): Observable<String> = getFuncOnIntervalObservable { nodeDetailsAdapter.getSyncProgressString() }

    private fun <T> getFuncOnIntervalObservable(function: () -> T) = Observable
            .interval(EMISSION_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .startWith(0)
            .map { function.invoke() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())

}

