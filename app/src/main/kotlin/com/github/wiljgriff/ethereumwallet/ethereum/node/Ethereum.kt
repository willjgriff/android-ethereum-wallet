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
class Ethereum(ethereumFilePath: String) {

    private val UPDATE_INTERVAL_SECONDS = 1L
    private val DEFAULT_TO_CURRENT_BLOCK_VALUE = -1L
    // TODO: Find out what this number means. It may be MB of cache for lightchaindata.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L

    private val node = Node(ethereumFilePath, NodeConfig())
    private val context = Context()
    private val ethereumClient: EthereumClient
    val cachedBlockHeaderObservable: Observable<Header> by lazy { getBlockHeaderObservable() }
    // This will have to change as we will want to pass in the address
    val cachedBalanceAtAddress: Observable<String> by lazy { getBalanceAtAddress() }

    init {
        node.start()
        ethereumClient = node.ethereumClient
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

    fun getBalanceAtAddress(): Observable<String> {
        val address: Address? = Address("0xfb1081ec00a46246d5bdc166e1cae6938723252c")
        return getBigIntReplayObservableForFunc { ethereumClient.getBalanceAt(context, address, DEFAULT_TO_CURRENT_BLOCK_VALUE) }
    }

    fun getPendingBalanceAtAddress(): Observable<String> {
        val address = Address("0xfb1081ec00a46246d5bdc166e1cae6938723252c")
        return getBigIntReplayObservableForFunc { ethereumClient.getPendingBalanceAt(context, address) }
    }

    private fun <T> getBigIntReplayObservableForFunc(function: () -> T) = Observable
            .just(function)
            .map { it.invoke() }
            .map { it.toString() }
            .replay(1)
            .autoConnect()
            .compose(AndroidIoTransformer())

    fun getNodeInfoString() = node.getNodeInfoString()

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