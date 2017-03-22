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
    // TODO: Find out what this number means.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L

    private val node = Node(ethereumFilePath, NodeConfig())
    private val context = Context()
    private var ethereumClient: EthereumClient

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
                .sample(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .compose(AndroidIoTransformer<Header>())
    }

    fun getPeersInfo(): Observable<PeerInfos> {
        return Observable
                .interval(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .map { node.peersInfo }
                .observeOn(AndroidSchedulers.mainThread())
    }
}