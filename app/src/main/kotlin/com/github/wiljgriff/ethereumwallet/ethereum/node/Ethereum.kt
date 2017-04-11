package com.github.wiljgriff.ethereumwallet.ethereum.node

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
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

    init {
        node.start()
        ethereumClient = node.ethereumClient
    }

    fun getBlockHeaderObservable(): Observable<Header> {
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

    fun getPeersInfo(): Observable<PeerInfos> {
        return getFuncOnIntervalObservable { node.peersInfo }
    }

    fun getNodePeersInfoString(): Observable<String> {
        return getFuncOnIntervalObservable { node.getPeersInfoString() }
    }

    fun getNodeInfoString(): String {
        return node.getNodeInfoString()
    }

    fun getSyncProgressString(): Observable<String> {
        return getFuncOnIntervalObservable { ethereumClient.getSyncProgressString(context) }
    }

    private fun <T> getFuncOnIntervalObservable(function: () -> T): Observable<T> {
        return Observable
                .interval(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .startWith(0)
                .map { function.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBalanceAtAddress() {
        ethereumClient.getBalanceAt(context, null, -1) // -1 should be null but can't be as it expects a primitive...
    }

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