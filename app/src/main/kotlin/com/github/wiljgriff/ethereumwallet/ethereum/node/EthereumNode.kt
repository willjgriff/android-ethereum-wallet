package com.github.wiljgriff.ethereumwallet.ethereum.node

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.ethereum.geth.*
import java.util.concurrent.TimeUnit

/**
 * Created by Will on 16/03/2017.
 *
 * TODO: Create an interface for this, this is likely to be swapped out in the future, parity...
 */
class EthereumNode(ethereumFilePath: String) {

    private val UPDATE_INTERVAL_SECONDS = 1L
    private val DEFAULT_TO_CURRENT_BLOCK_VALUE = -1L
    // TODO: Find out what this number means. It may be MB of cache for lightchaindata.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L

    private val node = Node(ethereumFilePath, NodeConfig())
    val context = Context()
    val ethereumClient: EthereumClient by lazy { node.ethereumClient }
    val cachedBlockHeaderObservable: Observable<Header> by lazy { getBlockHeaderObservable() }

    init {
        node.start()
    }

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
                .compose(AndroidIoTransformer<Header>())
                .throttleFirst(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .replay(10)
                .autoConnect()
    }

    fun getNodeInfoString() = node.getNodeInfoString()

    fun getBalanceAt(address: Address): BigInt = ethereumClient.getBalanceAt(context, address, DEFAULT_TO_CURRENT_BLOCK_VALUE)

    fun getPendingBalanceAt(address: Address) = ethereumClient.getPendingBalanceAt(context, address)

    fun getPeersInfo(): Observable<PeerInfos> = getFuncOnIntervalObservable { node.peersInfo }

    fun getNodePeerInfoStrings(): Observable<List<String>> = getFuncOnIntervalObservable { node.getPeersInfoStrings() }

    fun getSyncProgressString(): Observable<String> = getFuncOnIntervalObservable { ethereumClient.getSyncProgressString(context) }

    private fun <T> getFuncOnIntervalObservable(function: () -> T) = Observable
            .interval(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .startWith(0)
            .map { function.invoke() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())

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