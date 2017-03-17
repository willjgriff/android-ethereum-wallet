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
            }, 16)
        }
                .compose(AndroidIoTransformer<Header>())
    }

    fun getPeersInfo(): Observable<PeerInfos> {
        return Observable
                .interval(3, TimeUnit.SECONDS)
                .map { node.peersInfo }
                .observeOn(AndroidSchedulers.mainThread())
    }
}