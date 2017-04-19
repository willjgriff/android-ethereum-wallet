package com.github.willjgriff.ethereumwallet.ethereum.node

import com.github.willjgriff.ethereumwallet.data.extensions.androidIoSchedule
import com.github.willjgriff.ethereumwallet.data.extensions.replayConnect
import com.github.willjgriff.ethereumwallet.data.model.DomainHeader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class NodeDetails(private val nodeDetailsAdapter: NodeDetailsAdapter) {

    private val EMISSION_INTERVAL_SECONDS = 1L
    val cachedBlockHeaderObservable: Observable<DomainHeader> by lazy { getBlockHeaderObservable() }

    private fun getBlockHeaderObservable(): Observable<DomainHeader> {
        return Observable
                .create<DomainHeader> {
                    nodeDetailsAdapter.subscribeNewHeaderHandler(object : NewDomainHeaderListener {
                        override fun onNewDomainHeader(headHex: DomainHeader) {
                            it.onNext(headHex)
                        }

                        override fun onError(error: String) {
                            it.onError(Throwable(error))
                        }
                    })
                }
                .androidIoSchedule()
                .throttleFirst(EMISSION_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .replayConnect(10)
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

    interface NewDomainHeaderListener {
        fun onNewDomainHeader(headHex: DomainHeader)
        fun onError(error: String)
    }

}

