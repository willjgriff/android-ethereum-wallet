package com.github.wiljgriff.ethereumwallet.ethereum.node

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.ethereum.geth.Header
import org.ethereum.geth.NewHeadHandler
import org.ethereum.geth.PeerInfos
import java.util.concurrent.TimeUnit

/**
 * Created by williamgriffiths on 15/04/2017.
 *
 * TODO: Potentially make this class independant of the Ethereum implementation used so the only
 * thing that needs changing would be the ethereumDelegate
 */
class NodeDetails(private val ethereumDelegate: EthereumDelegate) {

    private val UPDATE_INTERVAL_SECONDS = 1L
    val cachedBlockHeaderObservable: Observable<Header> by lazy { getBlockHeaderObservable() }

    private fun getBlockHeaderObservable(): Observable<Header> {
        return Observable
                .create<Header> {
                    ethereumDelegate.subscribeNewHeaderHandler(object : NewHeadHandler {
                        override fun onNewHead(header: Header?) {
                            it.onNext(header)
                        }
                        override fun onError(error: String?) {
                            it.onError(Throwable(error))
                        }
                    })
                }
                .compose(AndroidIoTransformer<Header>())
                .throttleFirst(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .replay(10)
                .autoConnect()
    }

    fun getNodeInfoString() = ethereumDelegate.getNodeInfoString()

    fun getPeersInfo(): Observable<PeerInfos> = getFuncOnIntervalObservable { ethereumDelegate.getPeersInfo() }

    fun getNodePeerInfoStrings(): Observable<List<String>> = getFuncOnIntervalObservable { ethereumDelegate.getPeersInfoStrings() }

    fun getSyncProgressString(): Observable<String> = getFuncOnIntervalObservable { ethereumDelegate.getSyncProgressString() }

    private fun <T> getFuncOnIntervalObservable(function: () -> T) = Observable
            .interval(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .startWith(0)
            .map { function.invoke() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())

}