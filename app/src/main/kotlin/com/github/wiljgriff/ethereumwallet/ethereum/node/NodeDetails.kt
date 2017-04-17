package com.github.wiljgriff.ethereumwallet.ethereum.node

import com.github.wiljgriff.ethereumwallet.data.model.DomainHeader
import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class NodeDetails(private val nodeBridge: NodeBridge) {

    private val UPDATE_INTERVAL_SECONDS = 1L
    val cachedBlockHeaderObservable: Observable<DomainHeader> by lazy { getBlockHeaderObservable() }

    private fun getBlockHeaderObservable(): Observable<DomainHeader> {
        return Observable
                .create<DomainHeader> {
                    nodeBridge.subscribeNewHeaderHandler(object : NewDomainHeaderListener {
                        override fun onNewDomainHeader(headHex: DomainHeader) {
                            it.onNext(headHex)
                        }
                        override fun onError(error: String) {
                            it.onError(Throwable(error))
                        }
                    })
                }
                .compose(AndroidIoTransformer<DomainHeader>())
                .throttleFirst(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .replay(10)
                .autoConnect()
    }

    fun getNodeInfoString() = nodeBridge.getNodeInfoString()

    fun getPeersInfo(): Observable<Long> = getFuncOnIntervalObservable { nodeBridge.getPeersInfoSize() }

    fun getNodePeerInfoStrings(): Observable<List<String>> = getFuncOnIntervalObservable { nodeBridge.getPeersInfoStrings() }

    fun getSyncProgressString(): Observable<String> = getFuncOnIntervalObservable { nodeBridge.getSyncProgressString() }

    private fun <T> getFuncOnIntervalObservable(function: () -> T) = Observable
            .interval(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .startWith(0)
            .map { function.invoke() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())

    interface NewDomainHeaderListener {
        fun onNewDomainHeader(headHex: DomainHeader)
        fun onError(error: String)
    }

}