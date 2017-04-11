package com.github.wiljgriff.ethereumwallet.ethereum.node

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableRange
import org.ethereum.geth.*
import java.util.concurrent.TimeUnit

/**
 * Created by Will on 16/03/2017.
 */
class Ethereum(ethereumFilePath: String) {

    private val UPDATE_INTERVAL_SECONDS = 1L
    // TODO: Find out what this number means. It may be MB of cache for lightchaindata.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L

    private val node = Node(ethereumFilePath, NodeConfig())
    private val context = Context()
    private val ethereumClient: EthereumClient
    private val blockHeaderObservableo: Observable<Header> by lazy { getBlockHeaderObservable() }

    init {
        node.start()
        ethereumClient = node.ethereumClient
    }

    fun getCachedBlockHeaderObservable(): Observable<Header> = blockHeaderObservableo

    private fun getBlockHeaderObservable(): Observable<Header> {
        return Observable.create<Header> {
            ethereumClient.subscribeNewHead(context, object : NewHeadHandler {

                override fun onNewHead(header: Header?) {
                    it.onNext(header)
                }

                override fun onError(error: String?) {
                    it.onError(Throwable(error))
                }
            }, SOME_RANDOM_SAMPLING_SIZE)
        }
                .throttleFirst(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .compose(AndroidIoTransformer<Header>())
    }

    fun getNodeInfoString() = node.getNodeInfoString()

    fun getPeersInfo(): Observable<PeerInfos> = getFuncOnIntervalObservable { node.peersInfo }

    fun getNodePeerInfoStrings(): Observable<List<String>> = getFuncOnIntervalObservable { node.getPeersInfoStrings() }
            .distinctUntilChanged()

    fun getSyncProgressString(): Observable<String> = getFuncOnIntervalObservable { ethereumClient.getSyncProgressString(context) }

    private fun <T> getFuncOnIntervalObservable(function: () -> T) = Observable
            .interval(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .startWith(0)
            .map { function.invoke() }
            .observeOn(AndroidSchedulers.mainThread())

    fun getBalanceAtAddress() = ethereumClient.getBalanceAt(context, null, -1) // -1 should be null but can't be as it expects a primitive...

    fun potentiallyUsefulMethods() {

//        ethereumClient.sendTransaction()
//
//        ethereumClient.callContract()
//
//        ethereumClient.getPendingBalanceAt()
//
//        ethereumClient.getPendingTransactionCount()
//
//        ethereumClient.estimateGas()
    }

}